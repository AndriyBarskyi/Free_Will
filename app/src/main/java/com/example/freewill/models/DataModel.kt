package com.example.freewill.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class DataModel: ViewModel() {
    val message :MutableLiveData<Day> by lazy{
        MutableLiveData<Day>()
    }
}