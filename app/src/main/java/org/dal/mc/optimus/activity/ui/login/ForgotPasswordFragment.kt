package org.dal.mc.optimus.activity.ui.login

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_forgot_password.*

import org.dal.mc.optimus.R

class ForgotPasswordFragment : Fragment() {

    companion object {
        fun newInstance() = ForgotPasswordFragment()
    }

    private lateinit var viewModel: ForgotPasswordViewModel

    /** Retreiving the UI elements **/
    private var text_Email: EditText? = null
    private var btnSend: Button? = null

    /** Accesing Firebase **/
    private var firebaseAuth: FirebaseAuth ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_forgot_password, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ForgotPasswordViewModel::class.java)

        text_Email = et_email
        btnSend = btn_submit
        firebaseAuth = FirebaseAuth.getInstance()
        btnSend!!.setOnClickListener {
            resetEmail()
        }
    }

    /** function that send the mail to reset the password **/
    private fun resetEmail() {
        val email = text_Email?.text.toString()

        /** validating the Email **/
        if(!TextUtils.isEmpty(email)) {
            firebaseAuth!!
                .sendPasswordResetEmail(email)
                .addOnCompleteListener {task ->
                    if(task.isSuccessful) {
                        Toast.makeText(context, "Email Sent", Toast.LENGTH_SHORT).show()
                        navigateToLogin()
                    }
                    else {
                        Toast.makeText(context, "Enter Email", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    /** navigate to the login screen **/
    private fun navigateToLogin() {
        findNavController().navigate(R.id.navigation_my_signIn)
    }

}
