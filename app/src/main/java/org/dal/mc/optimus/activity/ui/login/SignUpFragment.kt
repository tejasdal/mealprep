package org.dal.mc.optimus.activity.ui.login

import android.app.ProgressDialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_signup.*
import org.dal.mc.optimus.R
import java.util.regex.Pattern


class SignUpFragment : Fragment() {

    companion object {
        fun newInstance() = SignUpFragment()
    }

    /** Initialising the text fields rendered from the UI **/
    private lateinit var viewModel: SignUpViewModel
    private var firstName: EditText? = null
    private var lastName: EditText? = null
    private var et_Email: EditText? = null
    private var et_Password_text: EditText? = null
    private var btnSignUp: Button? = null
    private var progressBar: ProgressDialog? = null
    private var dbReference: DatabaseReference? = null
    private var firebasedb: FirebaseDatabase? = null
    private lateinit var firebaseAuth: FirebaseAuth
    private val FRAGMENT_TAG = "SignUpFragment"

    /** Creating the global variables for the user details **/
    private var first_Name: String? = null
    private var last_Name: String? = null
    private var email: String? = null
    private var password: String? = null


    /** Password Validation pattern **/
    val password_Pattern = Pattern.compile(
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

    /** Password error message **/
    var passwordError = "Password should be between 8 to 24 character\n" +
            "at least 1 special character [@#$%^&+=]\n" +
            "at least 1 digit\n" +
            "at least 1 capital letter\n" +
            "at least 1 small letter\n"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createAccountFields()
    }

    private fun createAccountFields() {
        firstName = et_First_Name
        lastName = et_last_Name
        et_Email = et_email
        et_Password_text = et_Password
        btnSignUp = btn_SignUp
        progressBar = ProgressDialog(context)
        firebasedb = FirebaseDatabase.getInstance("https://mealprep-group18.firebaseio.com")
        dbReference = firebasedb!!.reference!!.child("Users")
        firebaseAuth = FirebaseAuth.getInstance()

        /** onclick of the sign up button **/
        btnSignUp!!.setOnClickListener {
            first_Name = et_First_Name?.text.toString()
            last_Name = et_last_Name?.text.toString()
            email = et_email?.text.toString()
            password = et_Password?.text.toString()

            if(validateEmail()!! && validatePassword()!! && isvalidFirstname()!! && isvalidLastname()!!) {
                validateAccount()
            }
        }
    }

    /** validate the account in firebase **/
    private fun validateAccount() {
        if(!TextUtils.isEmpty(first_Name) && !TextUtils.isEmpty(last_Name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            progressBar!!.setMessage("Registering User")
            progressBar!!.show()
            firebaseAuth.createUserWithEmailAndPassword(email!!, password!!).addOnCompleteListener { task ->
                    if(task.isSuccessful) {

                        /** Updating the Signed In Users Information **/
                        val user_Id = firebaseAuth!!.currentUser!!.uid

                        /** Verify the email address **/
                        verifyEmailAddress()

                        /** Update user information **/
                        val UserDb = dbReference!!.child(user_Id)
                        UserDb.child("email").setValue(email)
                        UserDb.child("firstName").setValue(first_Name)
                        UserDb.child("lastName").setValue(last_Name)

                        /** Update the user **/
                        navigateToProfile()
                    }
                    /** If sign in fails update the user **/
                    else {
                        Toast.makeText(context,"Authentication Failed",Toast.LENGTH_SHORT).show()
                        Log.w("Signup Error", "onCancelled", task.getException());
                    }
                    progressBar!!.hide()
                }
        }
        /** else Enter all the details **/
        else {
            Toast.makeText(context, "Enter all requiured details", Toast.LENGTH_SHORT).show()
        }
    }

    /** function that verifies the email address from the firebase **/
    private fun verifyEmailAddress() {
        val firebaseUser = firebaseAuth!!.currentUser;
        firebaseUser!!.sendEmailVerification()
            .addOnCompleteListener {task ->
                if (task.isSuccessful) {
                    Toast.makeText(context,"Verified Email Address" + firebaseUser.getEmail(),Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context,"Failed Email Address",Toast.LENGTH_SHORT).show()
                }
            }
    }
    fun isvalidFirstname(): Boolean? {
        first_Name = et_First_Name?.text.toString()
        return if(first_Name!!.isEmpty()) {
            ip_First_Name.setError("Field cannot be empty")
            false
        } else {
            ip_First_Name.setError(null)
            true
        }
    }

    fun isvalidLastname(): Boolean? {
        last_Name = et_last_Name?.text.toString()
        return if(last_Name!!.isEmpty()) {
            ip_First_Name.setError("Field cannot be empty")
            false
        } else {
            ip_First_Name.setError(null)
            true
        }
    }

    /** validating Email **/
    public fun validateEmail(): Boolean? {
        email = et_email?.text.toString()
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
        password = et_Password?.text.toString()
        return if (password!!.isEmpty()) {
            ip_layout_password_signup.setError("Field Cannot be empty")
            false
        } else if (!password_Pattern!!.matcher(password).matches()) {
            ip_layout_password_signup.setError(
                "Password should be between 8 to 24 character\n" +
                        "at least 1 special character [@#$%^&+=]\n" +
                        "at least 1 digit\n" +
                        "at least 1 capital letter\n" +
                        "at least 1 small letter\n"
            )
            et_Password_text!!.setText("")
            false
        } else {
            ip_layout_password_signup.setError(null)
            true
        }
    }

    /** function that navigates to Profile **/
    private fun navigateToProfile() {
        findNavController().navigate(R.id.navigation_profile)
    }

}
