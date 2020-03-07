package org.dal.mc.optimus.activity.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import org.dal.mc.optimus.R


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(this, Observer {
            textView.text = it
        })

        val searchBox = root.findViewById(R.id.search_bar) as EditText
        searchBox.setOnFocusChangeListener{view, hasFocus->moveToFragment(hasFocus)}

        return root
    }

    fun moveToFragment(hasFocus: Boolean){
        if(hasFocus) {
            Log.d("---->", "--About to call Search Fragment--")

            val fragment = SearchFragment()
            val transaction: FragmentTransaction ?= getFragmentManager()?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment, fragment)
            transaction?.addToBackStack(null);
            transaction?.commit()
        }
        else{
            Log.d("---->", "--About to call Search Fragment4--")
        }
    }
}