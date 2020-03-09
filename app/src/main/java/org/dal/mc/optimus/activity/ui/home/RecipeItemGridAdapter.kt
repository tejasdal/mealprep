package org.dal.mc.optimus.activity.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.dal.mc.optimus.model.RecipeItem

class RecipeItemGridAdapter(var listRecipe:List<RecipeItem>):
        RecyclerView.Adapter<RecipeItemGridViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeItemGridViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RecipeItemGridViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return listRecipe.size
    }

    override fun onBindViewHolder(holder: RecipeItemGridViewHolder, position: Int) {
        val recipeItem = listRecipe[position]
        return holder.bind(recipeItem)
    }
}