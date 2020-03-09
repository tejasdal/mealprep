package org.dal.mc.optimus.activity.ui.home

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
import org.dal.mc.optimus.model.RecipeItem


class RecipeItemViewHolder (inflater: LayoutInflater, parent: ViewGroup):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.recipe_list_item,parent, false)){

    private var recipeImg: ImageView
    private var recipeName: TextView
    init {
        //Bind image and text view with that of in layout.
        recipeImg = itemView.findViewById(R.id.img_recipe_item)
        recipeName = itemView.findViewById(R.id.txt_recipe_item)
    }

/**
 * Method to bind current data in List into layout items.
 */
    fun bind(recipeItem: RecipeItem){
        Glide.with(itemView).load(recipeItem.imgUrl).apply( RequestOptions()
            .transform( MultiTransformation( CenterCrop(),RoundedCorners(16))))
            .into(recipeImg)
        recipeName.text = recipeItem.recipeName
    }

}