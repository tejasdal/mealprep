package org.dal.mc.optimus.activity.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.dal.mc.optimus.R
import java.util.regex.Pattern

class ProfileFragment : Fragment() {

    private lateinit var dashboardViewModel: ProfileViewModel

    /** Accessing Fire base Components**/
    private var firebaseDbReference: DatabaseReference? = null
    private var firebasedb: DatabaseReference? = null
    private var firebaseAuth: FirebaseAuth? = null
    private var signOutBtn: Button? = null

    /** Request Code of any number **/
    val request_code: Int = 18
    val database = FirebaseDatabase.getInstance()

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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)

        signOutBtn = root.findViewById(R.id.sign_out_button)

        dashboardViewModel.text.observe(this, Observer {
            textView.text = it
        })

        /** triggering event on signing out the account **/
        signOutBtn!!.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            findNavController().navigate(R.id.navigation_my_signIn)
        }

        return root
    }

    /*
    /** On start of the application saving the user details **/
    override fun onStart() {
        super.onStart()
        val firebaseUser = firebaseAuth!!.currentUser
        val firebaseUserReference = firebaseDbReference!!.child(firebaseUser!!.uid)
        /*tvEmail!!.text = mUser.email*/
        /*tvEmailVerifiied!!.text = mUser.isEmailVerified.toString()*/
        /*firebaseUserReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                tvFirstName!!.text = snapshot.child("firstName").value as String
                tvLastName!!.text = snapshot.child("lastName").value as String
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })*/
    }

     */
}