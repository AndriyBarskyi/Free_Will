package com.example.freewill.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class DataModel: ViewModel() {
    val message :MutableLiveData<String> by lazy{
        MutableLiveData<String>()
    }
    val audutoria :MutableLiveData<String> by lazy{
        MutableLiveData<String>()
    }
}