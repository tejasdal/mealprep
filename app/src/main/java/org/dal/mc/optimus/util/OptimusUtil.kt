package org.dal.mc.optimus.util

import android.content.Context
import android.content.Intent
import org.dal.mc.optimus.model.RecipeItem
import java.util.*
import kotlin.Nothing as Nothing1
import kotlin.collections.ArrayList as ArrayList1

/**
 * Method to navigate to HomeActivity from any activity.
 * @param packageContext Context of the origin activity.
 * @param class          Class type of destination Activity.
 */
fun <T: Any?> navigateTo(packageContext: Context, clazz: Class<T> ){
    val intent = Intent(packageContext, clazz)
    packageContext.startActivity(intent)
}

//uploaded list
var uploadedRecipes: ArrayList<RecipeItem> = ArrayList()
fun fetchUploadedRecipes() : ArrayList<RecipeItem> {
    if (uploadedRecipes == null){
        uploadedRecipes = ArrayList<RecipeItem>()
    }
    return uploadedRecipes
}

//myRecipe list
 var myRecipes: ArrayList<RecipeItem> = ArrayList()
fun fetchMyRecipes() : ArrayList<RecipeItem> {
    if (myRecipes == null){
        myRecipes = ArrayList()
    }
    return myRecipes
}
