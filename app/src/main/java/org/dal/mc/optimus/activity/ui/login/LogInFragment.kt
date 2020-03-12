package org.dal.mc.optimus.activity.ui.login

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.fragment_login.*
import org.dal.mc.optimus.R
import java.util.regex.Pattern

class LogInFragment : Fragment() {

    companion object {
        fun newInstance() = LogInFragment()
    }

    private lateinit var viewModel: LogInViewModel

    /** Initialising the text fields rendered from the UI **/
    private var forgotPassword: TextView? = null
    private var email_text: EditText? = null
    private var editPassword: EditText? = null
    private var btnLogIn: Button? = null
    private var btnCreateAccount: Button? = null
    private var progressBar: ProgressDialog? = null

    /** Creating the global variables for the user details **/
    private var email: String? = null
    private var password: String? = null

    /** Firebase references **/
    private lateinit var firebaseAuth: FirebaseAuth
    private val FRAGMENT_TAG = "LogInFragment"

    /** For Google Sign In **/
    val signInCode: Int = 1
    lateinit var googleClient: GoogleSignInClient
    lateinit var SignInOptions: GoogleSignInOptions
    var backPressSingleTime: Boolean = false

    /** validation parameters **/
    val password_pattern: Pattern? = Pattern.compile(
        "^" +
                "(?=.*[0-9])" +  //at least 1 digit
                "(?=.*[a-z])" +  //at least 1 lower case letter
                "(?=.*[A-Z])" +  //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +  //any letter
                "(?=.*[@#$%^&+=])" +  //at least 1 special character
                "(?=\\S+$)" +  //no white spaces
                ".{8,24}" +  //at least between 8 to 24 characters
                "$"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root =  inflater.inflate(R.layout.fragment_login, container, false)
        val createAccountButton: Button = root.findViewById(R.id.btn_Create_Account)

        createAccountButton.setOnClickListener {
            findNavController().navigate(R.id.navigation_my_signUp)
        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LogInViewModel::class.java)

        /** starting the activity **/
        userActions()

        /** Google Sign In **/
        googleCOnfigurations()
        SignInToGoogle()
    }

    /** Ovverididng the onactivity result **/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == signInCode) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val getaccount = task.getResult(ApiException::class.java)
                if (getaccount != null) {
                    googleAuth(getaccount!!)
                }
            } catch (e: ApiException) {
                Toast.makeText(context, "Google SignIn Failed", Toast.LENGTH_LONG).show()
            }
        }
    }

    /** Ovverding the On start function **/
    override fun onStart() {
        super.onStart()
        val userDetails = FirebaseAuth.getInstance().currentUser
        if (userDetails != null) {
            navigateToProfile()
            // finish()
        }
    }

    /** function that performs the different types of user actions **/
    private fun userActions() {
        /** rendering the text views **/
        forgotPassword = tv_forgot_password
        email_text = et_email
        editPassword = et_Password
        btnLogIn = btn_Login
        btnCreateAccount = btn_Create_Account

        /**  Accesssing the progress of the firebase and authentication **/
        progressBar = ProgressDialog(context)
        firebaseAuth = FirebaseAuth.getInstance()

        /** Setting the listner events **/
        forgotPassword!!.setOnClickListener {
            findNavController().navigate(R.id.navigation_my_forgot_password)
        }

        btnCreateAccount!!.setOnClickListener {
            findNavController().navigate(R.id.navigation_my_signUp)
        }

        btnLogIn!!.setOnClickListener {
            if (validateEmail()!! and validatePassword()!!) {
                userLogin()
            }
        }
    }

    /** functions that performs the user login **/
    private fun userLogin() {
        if (isNetworkConnected())
        {
            /** Accessing the text fields **/
            email = et_email?.text.toString()
            password = et_Password?.text.toString()

            /** Authenticating with the firebase **/
            if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                progressBar!!.setMessage("Logging In User")
                progressBar!!.show()

                firebaseAuth!!.signInWithEmailAndPassword(email!!, password!!).addOnCompleteListener {
                    task -> progressBar!!.hide()
                    if(task.isSuccessful) {
                        navigateToProfile()
                    }
                    else {
                        Toast.makeText(context, "Authentication failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else {
                Toast.makeText(context, "Enter all details", Toast.LENGTH_SHORT).show()
            }
        }
        else {
            Toast.makeText(context,"Please check the internet connection",Toast.LENGTH_SHORT).show()
        }
    }

    /**  checking if Internet is connected or not **/
    private fun isNetworkConnected(): Boolean {
        val connectivityManager = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected
    }

    /** Upon Signing In navigating to profile page **/
    private fun navigateToProfile() {
        findNavController().navigate(R.id.navigation_profile)
    }

    /** Configure Google SignIn **/
    private fun googleCOnfigurations() {
        SignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleClient = GoogleSignIn.getClient(context!!, SignInOptions)
    }

    /** Performing the google Sign In **/
    private fun SignInToGoogle() {
        google_button.setOnClickListener {
            val intent: Intent = googleClient.signInIntent
            startActivityForResult(intent, signInCode)
        }
    }

    /**  firebase authentication with google **/
    private fun googleAuth(acct: GoogleSignInAccount) {
        progressBar!!.setMessage("Logging In User")
        progressBar!!.show()
        val cred = GoogleAuthProvider.getCredential(acct.idToken, null)
        firebaseAuth.signInWithCredential(cred).addOnCompleteListener {
            if (it.isSuccessful) {
                navigateToProfile()
            } else {
                Toast.makeText(context, "Google SignIn Failed", Toast.LENGTH_LONG).show()
            }
        }
        progressBar!!.hide()
    }

    /** validating Email **/
    public fun validateEmail(): Boolean? {
        email = et_email.getText().toString().trim({ it <= ' ' })
        return if (email!!.isEmpty()) {
            ip_layout_email.setError("Field cannot be empty")
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            ip_layout_email.setError("Please enter a valid EMAIL address")
            false
        } else {
            ip_layout_email.setError(null)
            true
        }
    }

    /** validating password **/
    public fun validatePassword(): Boolean? {
        password = et_Password.getText().toString().trim({ it <= ' ' })
        return if (password!!.isEmpty()) {
            ip_layout_Password.setError("Field Cannot be empty")
            false
        } else if (!password_pattern!!.matcher(password).matches()) {
            ip_layout_Password.setError(
                "Password should be between 8 to 24 character\n" +
                        "at least 1 special character [@#$%^&+=]\n" +
                        "at least 1 digit\n" +
                        "at least 1 capital letter\n" +
                        "at least 1 small letter\n"
            )
            et_Password.setText("")
            false
        } else {
            ip_layout_Password.setError(null)
            true
        }
    }
}
