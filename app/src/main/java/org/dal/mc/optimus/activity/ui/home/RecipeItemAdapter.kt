package org.dal.mc.optimus.activity.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.dal.mc.optimus.model.RecipeItem

class RecipeItemAdapter(var listRecipe:List<RecipeItem>):
        RecyclerView.Adapter<RecipeItemViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return RecipeItemViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return listRecipe.size
    }

    override fun onBindViewHolder(holder: RecipeItemViewHolder, position: Int) {
        val recipeItem = listRecipe[position]
        return holder.bind(recipeItem)
    }
}