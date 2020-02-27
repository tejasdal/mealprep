package org.dal.mc.optimus.activity.ui.myrecipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyRecipeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is My Recipe Fragment"
    }
    val text: LiveData<String> = _text
}