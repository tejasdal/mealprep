package org.dal.mc.optimus.activity.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import org.dal.mc.optimus.R
import org.dal.mc.optimus.util.showKeyboard


var SEARCH_STRING = ""

class SearchFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("---->","--Starting Search Fragment--")
        val root = inflater.inflate(R.layout.fragment_search, container, false)

        val back_btn = root.findViewById(R.id.backButton) as Button
        val search_text_field = root.findViewById(R.id.search_bar) as EditText

        search_text_field.showKeyboard()

        back_btn.setOnClickListener{
            fragmentManager?.popBackStack()
        }

        search_text_field.setOnEditorActionListener { v, actionId, event ->  if(actionId == EditorInfo.IME_ACTION_DONE) {
            SEARCH_STRING = search_text_field.text.toString().trim()
            true
        } else {
            false
        }}

        performSearch()

        return root
    }

    fun performSearch(){
        if (!SEARCH_STRING.equals("")){

        }
    }
}
