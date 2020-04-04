package org.dal.mc.optimus.activity.ui.recipe

import android.app.LauncherActivity
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ShareCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_recipe.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_my_recipe.*
import org.dal.mc.optimus.R
import org.dal.mc.optimus.activity.ui.home.RecipeItemAdapter
import org.dal.mc.optimus.model.IngredientItem
import org.dal.mc.optimus.model.RecipeItem
import org.dal.mc.optimus.util.fetchMyRecipes
import java.lang.StringBuilder
import java.util.function.Predicate

class RecipeActivity : AppCompatActivity() {

    private var isLiked:Boolean = false
    private lateinit var recipeItem: RecipeItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)
        //set toolbar
        this.setSupportActionBar(app_bar_recipe)
        //to enable back button on app bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        //fetch recipe item
        recipeItem = intent.getSerializableExtra("recipe") as RecipeItem
        setRecipeName()
        //set video view
        var pathVideo = "android.resource://"+packageName+"/raw/recipe1"
        var uri = Uri.parse(pathVideo)
        recipe_video_view.setVideoURI(uri)
        recipe_video_view.start()
        initializeIngredientList()
        initializeNutritionList()
        initializeRelatedRecipeList()
        exportNutritionList()
        viewHideNutritionInfo()
        setRatingDialoge()
    }

    private fun setRecipeName() {
        txt_recipe_name.setText(recipeItem.recipeName)
    }

    /**
     * Method to open rating dialog box when click on I made this button.
     */
    private fun setRatingDialoge(){
        //set onClick listener on I made this button to rate the recipe.
        btn_rating.setOnClickListener {
            //Open rating dialog.
            var ratingDialog = RatingDialog()
            ratingDialog.show(supportFragmentManager, "")
        }
    }

    /**
     * Method to view or hide nutrition info.
     */
    private fun viewHideNutritionInfo(){
        var hideStr = "Hide info –"
        var viewStr = "View Info +"

        btn_hide_info.setOnClickListener {
            if(btn_hide_info.text.equals(hideStr)){
                //Hide nutrition recycler view and change text to viewStr.
//                list_nutrition.alpha = 0F
                list_nutrition.visibility = View.GONE
                btn_hide_info.text = viewStr
            } else{
                //Show nutrition recycler view and change text to hideStr.
//                list_nutrition.alpha = 1F
                list_nutrition.visibility = View.VISIBLE
                btn_hide_info.text = hideStr
            }
        }
    }

    /**
     * Method to export nutrition list to other Apps.
     */
    private fun exportNutritionList(){
        //1.set Onclick listener on export button.
        btn_export_nutrition.setOnClickListener {
            //2. convert Nutrition list to text
            var content = convertNutritionListToText()
            //3. Build share intent and share
            var shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain").setText(content).intent
            if (shareIntent.resolveActivity(packageManager) != null){
                startActivity(shareIntent)
            }
        }
    }

    /**
     * Method to convert List to Text
     * @return string of list.
     */
    private fun convertNutritionListToText(): String{
        var adapter: IngredientListAdapter = list_nutrition.adapter as IngredientListAdapter
        var recipeName = txt_recipe_name.text
        var finalText = StringBuilder(recipeName)
        for (item in adapter.listIngredients){
            finalText.append("\n").append(item.ingredientValue).append("  ").append(item.ingredientName)
        }
        return finalText.toString()
    }
    /**
     * Method to initialize related recipes list
     */
    private fun initializeRelatedRecipeList(){

        list_related_recipe.apply {
            //set layout manager
            layoutManager = LinearLayoutManager(this@RecipeActivity, LinearLayoutManager.HORIZONTAL, false)
            /**
             * This list is for Test. This should be removed with actual data of recipes.
             *
             */
            var listRecipes: List<RecipeItem> = listOf(
                RecipeItem("https://www.bbcgoodfood.com/sites/default/files/styles/recipe/public/recipe_images/chow-mein.jpg?itok=KbV5ifES","Noodles"),
                RecipeItem("https://www.bbcgoodfood.com/sites/default/files/styles/recipe/public/recipe/recipe-image/2016/07/green-rainbow-smoothie-bowl.jpg?itok=G1NBkie9","Green Rainbow Smoothie Bowl"),
                RecipeItem("https://www.bbcgoodfood.com/sites/default/files/editor_files/2019/12/rainbow-winter-dips-and-crudites-700-350.jpg","Rainbow winter dips & crudités"),
                RecipeItem("https://www.bbcgoodfood.com/sites/default/files/editor_files/2019/12/curried-fishcake-bites-700-350.jpg","Curried fishcake bites"),
                RecipeItem("https://www.bbcgoodfood.com/sites/default/files/styles/recipe/public/recipe/recipe-image/2018/01/jam-tarts.jpg?itok=3gXPOr4S","Jam Tarts"),
                RecipeItem("https://www.bbcgoodfood.com/sites/default/files/styles/recipe/public/recipe/recipe-image/2016/10/melting-heart-muffins.jpg?itok=2Fi0MSt_","Melting Heart Muffins"),
                RecipeItem("https://www.bbcgoodfood.com/sites/default/files/styles/recipe/public/recipe_images/recipe-image-legacy-id--693589_11.jpg?itok=ATK3H66T","Pudding")
                )
            adapter = RecipeItemAdapter(listRecipes)
        }
    }

    /**
     * Method to initialize nutrition list
     */
    private fun initializeNutritionList() {
        list_nutrition.apply {
            //set layout manager
            layoutManager = LinearLayoutManager(this@RecipeActivity)
            isNestedScrollingEnabled = false
            /*
            * Sample list of Ingredients
             */
            var ingredients: List<IngredientItem> = listOf(
                IngredientItem("calories", "531"),
                IngredientItem("fat", "30 g"),
                IngredientItem("carbohydrates", "27 g"),
                IngredientItem("fiber", "4 g"),
                IngredientItem("sugar", "5 g"),
                IngredientItem("protein", "30 g")
            )
            adapter = IngredientListAdapter(ingredients)
        }
    }
    /**
     * Method to initialize ingredient list
     */
    private fun initializeIngredientList(){
        list_ingredinets.apply {
            //set layout manager
            layoutManager = LinearLayoutManager(this@RecipeActivity)
            isNestedScrollingEnabled = false
            /*
            * Sample list of Ingredients
             */
            var ingredients: List<IngredientItem> = listOf(
                IngredientItem("banana pudding mix", "3.4oz"),
                IngredientItem("large ripe banana", "2"),
                IngredientItem("egg", "2"),
                IngredientItem("vanilla extract", "1 teaspoon"),
                IngredientItem("cinnamon", "1 teaspoon")
            )
            adapter = IngredientListAdapter(ingredients)
        }
    }

    override fun onPause() {
        super.onPause()
        recipe_video_view.stopPlayback()
    }

    //Initialize menu in App bar.
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var menuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.recipe_app_bar_menu,menu)
        return true
    }

    //Handle menu select
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //TODO: Handle select event on menu. Add switch statement on Id of menu item and add logic for each one.
        return when(item.itemId){
            R.id.recipe_app_bar_share -> {
                // Build share intent and share
                var shareIntent = ShareCompat.IntentBuilder.from(this)
                    .setType("text/plain").setText("https://mealprep.com/recipe/hTy29D4er").intent
                if (shareIntent.resolveActivity(packageManager) != null){
                    startActivity(shareIntent)
                }
                true
            }
            R.id.recipe_app_bar_like -> {
                var user = FirebaseAuth.getInstance().currentUser
                if (user != null){
                    //User is signed in.
                    //on Like menu item selected
                    if (isLiked){
                        //already like, then disliked this.
                        isLiked = false
                        item.setIcon(R.drawable.ic_favorite_border)
                        Snackbar.make(window.decorView.findViewById(R.id.recipe_app_bar_like),
                            "Recipe removed from MyRecipe!", Snackbar.LENGTH_SHORT).show()
                        fetchMyRecipes().removeIf(Predicate { item -> item.recipeName.equals(recipeItem.recipeName) })
                    }else{
                        isLiked = true
                        item.setIcon(R.drawable.ic_favorite)
                        Snackbar.make(window.decorView.findViewById(R.id.recipe_app_bar_like),
                            "Recipe added to MyRecipe!", Snackbar.LENGTH_SHORT).show()
                        fetchMyRecipes().add(recipeItem)
                    }
                }else {
                    //User is not signed in. Redirect them to login page.
                    Snackbar.make(window.decorView.findViewById(R.id.recipe_app_bar_like),
                        "Please sign in to add recipe to MyRecipe!", Snackbar.LENGTH_SHORT).show()
                }
                true
            }
            R.id.home -> {
                onBackPressed()
                true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }
}
