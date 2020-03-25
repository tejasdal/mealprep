package org.dal.mc.optimus.activity.ui.recipe

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import org.dal.mc.optimus.R
import org.dal.mc.optimus.model.IngredientItem
import org.dal.mc.optimus.model.RecipeItem
import org.dal.mc.optimus.util.navigateTo

class IngredientListViewHolder (inflater: LayoutInflater, parent: ViewGroup):
    RecyclerView.ViewHolder(inflater.inflate(R.layout.ingredient_list_item,parent, false)){
    private var ingredientName: TextView
    private var ingredientValue: TextView
    init {
        ingredientName = itemView.findViewById(R.id.txt_ingredient_name)
        ingredientValue = itemView.findViewById(R.id.txt_ingredient_value)
    }

    /**
     * Method to bind current data in List into layout items.
     */
    fun bind(ingredientsItem: IngredientItem){
        ingredientName.text = ingredientsItem.ingredientName
        ingredientValue.text = ingredientsItem.ingredientValue
    }
}