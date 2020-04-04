package org.dal.mc.optimus.activity.ui.profile

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_recipe.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_profile.*
import org.dal.mc.optimus.R
import org.dal.mc.optimus.activity.ui.home.RecipeItemAdapter
import org.dal.mc.optimus.activity.ui.upload.UploadRecipe
import org.dal.mc.optimus.model.RecipeItem
import org.dal.mc.optimus.util.fetchUploadedRecipes
import java.util.regex.Pattern

class ProfileFragment : Fragment() {


    private lateinit var dashboardViewModel: ProfileViewModel

    /** Accessing Fire base Components**/
    private var firebaseDbReference: DatabaseReference? = null
    private var firebasedb: DatabaseReference? = null
    private var firebaseAuth: FirebaseAuth? = null
    private var signOutBtn: Button? = null

    /** Request Code of any number **/
    val request_code: Int = 18
    val database = FirebaseDatabase.getInstance()

    /** validation parameters **/
    val password_pattern: Pattern? = Pattern.compile(
        "^" +
                "(?=.*[0-9])" +  //at least 1 digit
                "(?=.*[a-z])" +  //at least 1 lower case letter
                "(?=.*[A-Z])" +  //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +  //any letter
                "(?=.*[@#$%^&+=])" +  //at least 1 special character
                "(?=\\S+$)" +  //no white spaces
                ".{8,24}" +  //at least between 8 to 24 characters
                "$"
    )

    //to check whether upload button is clicked or not.
    private var isUpload = false
    private var ACTION_VIDEO = 1
    private var PICK_VIDEO = 2

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        var root = inflater.inflate(R.layout.fragment_profile, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var user = FirebaseAuth.getInstance().currentUser
        if (user != null){
            uploadRecipe()
            setProfileData(user)
            setRecentlyViewedList()
            setUploadedRecipeList()
            signOut()
        }else {
            //User is not signed in. Redirect them to login page.
            findNavController().navigate(R.id.navigation_my_signIn)
        }
    }

    private fun setUploadedRecipeList() {
        //Initialize recycler view for Uploaded list.
        list_uploaded.apply {
            //set layout manager
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = RecipeItemAdapter(fetchUploadedRecipes())
        }
    }

    private fun setRecentlyViewedList() {
        //Initialize recycler view for recently viewed.
        list_recently_viewed.apply {
            //set layout manager
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
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
     * Method to fetch user profile image and name.
     */
    private fun setProfileData(user: FirebaseUser) {
        Glide.with(this).load(user.photoUrl)
            .apply( RequestOptions()
            .transform( MultiTransformation(RoundedCorners(250))))
            .into(img_profile)
        txt_user_name_profile.setText(user.displayName)
    }

    /**
     * Method to sign out from the application.
     */
    private fun signOut(){

        /** triggering event on signing out the account **/
            btn_logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            findNavController().navigate(R.id.navigation_my_signIn)
        }
    }

    private fun uploadRecipe(){
        fab_upload.setOnClickListener {
//            if (isUpload){
//                //if clicked on cross button, show uplaod button and hide gallery and camera button.
//                fab_camera.hide()
//                fab_gallery.hide()
//                fab_upload.text = "Upload"
//                fab_upload.extend(true)
//                fab_upload.icon = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_file_upload)
//                isUpload = false
//            }else {
//                fab_camera.show()
//                fab_gallery.show()
//                fab_upload.setText("")
//                fab_upload.shrink(true)
//                fab_upload.icon = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_close)
//                isUpload = true
//            }
            openGallery()
        }
        fab_camera.setOnClickListener {
            //open Camera on clicked on camera button.
            openCamera()
        }
        fab_gallery.setOnClickListener {
            //open Gallery on cliked on gallery button.
            openGallery()
        }
    }

    private fun openCamera(){

        Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA).also {takeVideoIntent ->
            takeVideoIntent.resolveActivity(requireActivity().packageManager)?.also {
                startActivityForResult(takeVideoIntent, ACTION_VIDEO)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        var videoUri: Uri? = null
        super.onActivityResult(requestCode, resultCode, intent)
        if ((requestCode == ACTION_VIDEO || requestCode == PICK_VIDEO) && resultCode == Activity.RESULT_OK){
            videoUri = intent?.data as Uri
        }
        if (videoUri != null) {
            //You got video URI now open it in new activity and display the video.
            //Traverse to Upload Activity
            val uploadIntent = Intent(context, UploadRecipe::class.java)
            uploadIntent.putExtra("path", videoUri.toString())
            startActivity(uploadIntent)
        }
    }

    private fun openGallery(){
        Intent(Intent.ACTION_PICK, MediaStore.Video.Media.INTERNAL_CONTENT_URI).also {
            startActivityForResult(it, PICK_VIDEO )
        }
    }
}