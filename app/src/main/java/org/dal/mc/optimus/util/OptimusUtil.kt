package org.dal.mc.optimus.util

import android.content.Context
import android.content.Intent

/**
 * Method to navigate to HomeActivity from any activity.
 * @param packageContext Context of the origin activity.
 * @param class          Class type of destination Activity.
 */
fun <T: Any?> navigateTo(packageContext: Context, clazz: Class<T> ){
    val intent = Intent(packageContext, clazz)
    packageContext.startActivity(intent)
}
