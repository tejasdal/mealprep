package org.dal.mc.optimus.activity.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
//        Just remove the dialog box.
        btn_rating_like.setOnClickListener {
            Snackbar.make(
                activity!!.findViewById(R.id.recipe_app_bar_like),
                "I liked it!", Snackbar.LENGTH_SHORT).show()
            dismiss()
        }
        btn_rating_dislike.setOnClickListener {
            Snackbar.make(
                activity!!.findViewById(R.id.recipe_app_bar_like),
                "Oops! I didn't like it!", Snackbar.LENGTH_SHORT).show()
            dismiss()
        }
        btn_rating_exit.setOnClickListener {
            Snackbar.make(
                activity!!.findViewById(R.id.recipe_app_bar_like),
                "Never mind!", Snackbar.LENGTH_SHORT).show()
            dismiss()
        }
    }
}