package org.dal.mc.optimus.activity.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.dialog_rating.*
import org.dal.mc.optimus.R

class RatingDialog : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = activity?.layoutInflater?.inflate(R.layout.dialog_rating,container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Toast.makeText(activity,"view created", Toast.LENGTH_SHORT).show()
//        Just remove the dialog box.
        btn_rating_dilike.setOnClickListener {
            Snackbar.make(
                activity!!.findViewById(R.id.recipe_app_bar_like),
                "I liked it!", Snackbar.LENGTH_SHORT).show()
            dismiss()
        }
        btn_rating_like.setOnClickListener {
            Snackbar.make(
                activity!!.findViewById(R.id.recipe_app_bar_like),
                "Oops! I don't liked it!", Snackbar.LENGTH_SHORT).show()
            dismiss()
        }
        btn_rating_exit.setOnClickListener {
            Snackbar.make(
                activity!!.findViewById(R.id.recipe_app_bar_like),
                "Nevermind!", Snackbar.LENGTH_SHORT).show()
            dismiss()
        }
    }
}