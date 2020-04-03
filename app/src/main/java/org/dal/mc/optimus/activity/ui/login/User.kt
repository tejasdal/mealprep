package org.dal.mc.optimus.activity.ui.login

/** User class for storing user details **/
class User {

    var first_name: String? = null
    var last_name: String? = null
    var email: String? = null

    fun User() {
    }

    fun User(first_name: String?, last_name: String?, email: String?) {
        this.first_name = first_name
        this.last_name = last_name
        this.email = email
    }
}