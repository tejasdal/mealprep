package org.dal.mc.optimus.activity.ui.recipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.dal.mc.optimus.activity.ui.home.RecipeItemGridViewHolder
import org.dal.mc.optimus.model.IngredientItem
import org.dal.mc.optimus.model.RecipeItem

class IngredientListAdapter(var listIngredients:List<IngredientItem>):
    RecyclerView.Adapter<IngredientListViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return IngredientListViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return listIngredients.size
    }

    override fun onBindViewHolder(holder: IngredientListViewHolder, position: Int) {
        val ingredientItem = listIngredients[position]
        return holder.bind(ingredientItem)
    }
}