package org.dal.mc.optimus.activity.ui.login

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.dialog_forgot_password.*
import kotlinx.android.synthetic.main.dialog_rating.*
import org.dal.mc.optimus.R

class ForgotPasswordDialog : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = activity?.layoutInflater?.inflate(R.layout.dialog_forgot_password,container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_forgot.setOnClickListener {



        }
    }

    /** function that send the mail to reset the password **/
    private fun resetEmail() {
        val email = edit_text_forgot_email?.text.toString()

        /** validating the Email **/
        if(!TextUtils.isEmpty(email)) {
            FirebaseAuth.getInstance()!!
                .sendPasswordResetEmail(email)
                .addOnCompleteListener {task ->
                    if(task.isSuccessful) {
                        Snackbar.make(
                            activity!!.findViewById(R.id.recipe_app_bar_like),
                            "Email Sent!", Snackbar.LENGTH_SHORT).show()
                            dismiss()
                    }
                    else {
                        Snackbar.make(
                            activity!!.findViewById(R.id.recipe_app_bar_like),
                            "Failed to sent Email.", Snackbar.LENGTH_SHORT).show()
                        dismiss()
                    }
                }
        } else{
            edit_text_forgot_email.setError("Please enter Email Address!")
        }
    }
}