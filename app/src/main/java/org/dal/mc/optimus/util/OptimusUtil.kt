package org.dal.mc.optimus.util

import android.content.Context
import android.content.Intent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * Method to navigate to HomeActivity from any activity.
 * @param packageContext Context of the origin activity.
 * @param class          Class type of destination Activity.
 */
fun <T: Any?> navigateTo(packageContext: Context, clazz: Class<T> ){
    val intent = Intent(packageContext, clazz)
    packageContext.startActivity(intent)
}

/**
 * Method to show keyboard when user clicks on EditText
 * Usage: edit_text_name.showKeyboard()
 */
fun EditText.showKeyboard() {
    post {
        requestFocus()
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }
}

/**
 * Method to show keyboard when user clicks on EditText
 * Usage: edit_text_name.hideKeyBoard()
 */
fun EditText.hideKeyBoard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
}