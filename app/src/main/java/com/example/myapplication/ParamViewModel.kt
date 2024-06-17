package com.example.myapplication

import android.util.Log
import androidx.lifecycle.ViewModel

class ParamViewModel(private val uid: String = "") : ViewModel() {


    fun asd(){
        Log.e("asd" , uid)
    }

}