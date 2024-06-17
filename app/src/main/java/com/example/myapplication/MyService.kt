package com.example.myapplication

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MyService : Service() {


    private val mBinder = object : IMyAidlInterface.Stub() {
        override fun basicTypes(
            anInt: Int,
            aLong: Long,
            aBoolean: Boolean,
            aFloat: Float,
            aDouble: Double,
            aString: String?
        ) {
            Log.e("aidl", "service 进程${Utils.getProcessName(this@MyService)} $anInt $aLong $aBoolean $aFloat $aDouble $aString")
        }

        override fun calculate(a: Int, b: Int): Int {
            Log.e("asd" , "service calculate 进程${Utils.getProcessName(this@MyService)}")
            return a + b
        }

        override fun greet(name: String?): String {
            Log.e("asd" , "service greet 进程${Utils.getProcessName(this@MyService)}")
            return "hello aidl"
        }

    }

    override fun onCreate() {
        super.onCreate()
        Log.e("asd" , "service onCreate 进程${Utils.getProcessName(this)}")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e("asd" , "service onStartCommand 进程${Utils.getProcessName(this)}")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder {
        return mBinder
    }


}