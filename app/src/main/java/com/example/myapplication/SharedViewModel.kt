package com.example.myapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel(private val app: Application) : ViewModel() {
    val data = MutableLiveData<String>()
    fun a() {
        data.value = "asd"
    }
}