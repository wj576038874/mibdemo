package com.example.myapplication

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.dao.DaoMaster
import com.dao.UserDao

class MyAp : Application(), ViewModelStoreOwner {

    private var mViewModelStore: ViewModelStore? = null
    private var sharedViewModel: SharedViewModel? = null

    override fun onCreate() {
        super.onCreate()
        mViewModelStore = ViewModelStore()

        sharedViewModel =
            ViewModelProvider(this, ApplicationFactory(this))[SharedViewModel::class.java]
    }

    override val viewModelStore: ViewModelStore
        get() = mViewModelStore!!


    fun getShareViewModel() = sharedViewModel!!
}