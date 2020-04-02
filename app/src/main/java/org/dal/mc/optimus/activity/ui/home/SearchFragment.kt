package org.dal.mc.optimus.activity.ui.home

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.*
import android.widget.ListAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_search.*
import org.dal.mc.optimus.R
import org.dal.mc.optimus.activity.ui.recipe.RecipeActivity
import org.dal.mc.optimus.model.RecipeItem
import org.dal.mc.optimus.util.navigateTo


class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_search, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //set toolbar
//        (activity as AppCompatActivity).setSupportActionBar(appbarse)
        setHasOptionsMenu(true)

        chip_1.setOnClickListener {
            openSearchResults()
        }
        chip_2.setOnClickListener {
            openSearchResults()
        }
        chip_2_1.setOnClickListener {
            openSearchResults()
        }
        chip_2_2.setOnClickListener {
            openSearchResults()
        }
        chip_2_3.setOnClickListener {
            openSearchResults()
        }
        chip_2_4.setOnClickListener {
            openSearchResults()
        }
        chip_2_5.setOnClickListener {
            openSearchResults()
        }
        chip_3_2.setOnClickListener {
            openSearchResults()
        }
        chip_3_1.setOnClickListener {
            openSearchResults()
        }
    }

    private fun openSearchResults(){
        findNavController().navigate(R.id.action_searchFragment_to_searchResultFragment)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater?.inflate(R.menu.search_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    //Handle menu select
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //TODO: Handle select event on menu. Add switch statement on Id of menu item and add logic for each one.
        return when(item.itemId){
            R.id.search_btn -> {
                openSearchResults()
                true
            }
            else -> {
                false
            }
        }
    }
}