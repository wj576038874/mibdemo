package com.example.myapplication

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class MyViewModel(application: Application) : AndroidViewModel(application) {


    val data = MutableLiveData<String>()

    fun asd(){
        data.value = "asd"
        Log.e("asd" , getApplication<MyAp>().toString())
    }

}