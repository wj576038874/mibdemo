package com.example.myapplication

import android.content.ComponentName
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.RemoteException
import android.provider.CallLog
import android.provider.MediaStore
import android.provider.Telephony
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Long
import java.util.Date
import java.util.concurrent.Executor


class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    private var mBinder: IMyAidlInterface? = null

    private val uid = "123"

    private lateinit var imageView: ImageView

    private val paramViewModel by viewModels<ParamViewModel> {


//        object : ViewModelProvider.Factory {
//            @Suppress("UNCHECKED_CAST")
//            override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                if (modelClass.isAssignableFrom(ParamViewModel::class.java)) {
//                    return ParamViewModel("123") as T
//                }
//                throw Exception("")
//            }
//        }

        object : AbstractSavedStateViewModelFactory() {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {
                return ParamViewModel(uid) as T
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        imageView = findViewById(R.id.image)
        val myViewModel = ViewModelProvider(this)[MyViewModel::class.java]

        paramViewModel.asd()

        myViewModel.getApplication<MyAp>().getShareViewModel().a()

        (application as MyAp).getShareViewModel().data.observe(this) {
//            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }


        myViewModel.asd()

        Log.e("asd", myViewModel.toString())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            registerScreenCaptureCallback(mainExecutor) {
                Toast.makeText(this, "截屏操作", Toast.LENGTH_SHORT).show()
                LoaderManager.getInstance(this).initLoader(3, null, this)
            }
        } else {
            var lastUri : String? = null
            contentResolver.registerContentObserver(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                true,
                object : ContentObserver(
                    Handler(
                        Looper.getMainLooper()
                    )
                ) {
                    override fun onChange(selfChange: Boolean, uri: Uri?) {
                        super.onChange(selfChange, uri)
                        if (uri?.toString() == lastUri){
                            return
                        }
                        //content://media/external/images/media/1000000127
                        //content://media/external/images/media/1000000127
                        //content://media/external/images/media/1000000127
                        lastUri = uri?.toString()
//                        imageView.setImageURI(ContentUris.withAppendedId(
//                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                            1000000127
//                        ))
                        Log.e("asd", "媒体库发生变化 ${Thread.currentThread().name}   $selfChange  " + uri.toString() +"   lastUri:$lastUri")
                        LoaderManager.getInstance(this@MainActivity).initLoader(3, null, this@MainActivity)
                    }
                })
        }

        findViewById<Button>(R.id.btn).setOnClickListener {
//            val intent = Intent(this, MyService::class.java)
//            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)

            val process = Runtime.getRuntime().exec("ping -c 5 www.baidu.com")
            process?.let {
                val bufferedReader = BufferedReader(InputStreamReader(it.inputStream))
                bufferedReader.forEachLine {
                    Log.e("asd", it)
                }
            }
        }


        val args = Bundle().also {
            it.putString("body", "您的验证码是：112233，请勿泄露")
        }


    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mBinder = IMyAidlInterface.Stub.asInterface(service)
            try {
                mBinder?.basicTypes(1, 2, true, 1.2f, 2.0, "aidl")
                val result = mBinder?.calculate(5, 3)
                val greeting = mBinder?.greet("Alice")
                Log.d("aidl", "${Utils.getProcessName(this@MainActivity)} Result: $result")
                Log.d("aidl", "${Utils.getProcessName(this@MainActivity)} Greeting: $greeting")
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mBinder = null
        }

    }


    private fun requestDefaultSmsApp() {
        if (Telephony.Sms.getDefaultSmsPackage(this) != packageName) {
            val intent = Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT)
            intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, packageName)
            startActivityForResult(intent, 1)
        } else {
            Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        Log.e("asd", "onCreateLoader ${Thread.currentThread().name} ${args?.getString("capture")}")
//        val queryUri = Uri.parse("content://sms/")
//        val projection = arrayOf("_id", "address", "person", "body", "date", "type")
//        return CursorLoader(this, queryUri, projection, null, null, "date desc limit 100")
//        return SmsCursorLoader.newInstance(this, args)
//        return CallLogCursorLoader.newInstance(this, args)
        return MediaCursorLoader.newInstance(this, args)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        Log.e("asd", "onLoaderReset ${Thread.currentThread().name}")
        loader.reset()
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        Log.e("asd", "onLoadFinished ${Thread.currentThread().name} $data")
        data?.let { cursor ->

            cursor.columnNames.forEach {
                Log.e("asd", it)
            }

            while (cursor.moveToNext()) {
                try {
                    //sms
//                    val id = cursor.getLong(cursor.getColumnIndex("_id"))
//                    val smsType = cursor.getString(cursor.getColumnIndex("type"))
//                    val smsNumber = cursor.getString(cursor.getColumnIndex("address"))
//                    val smsBody = cursor.getString(cursor.getColumnIndex("body"))
//                    val smsDate = cursor.getString(cursor.getColumnIndex("date"))
//                    Log.e("asd", "$id $smsType $smsNumber $smsBody $smsDate")

                    //calllog
//                    CallLog.Calls._ID
//                    CallLog.Calls.NUMBER,
//                    CallLog.Calls.DATE,
//                    CallLog.Calls.DURATION,
//                    CallLog.Calls.TYPE,
//                    CallLog.Calls.NEW,
//                    val id = cursor.getLong(cursor.getColumnIndex(CallLog.Calls._ID))
//                    val number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER))
//                    val DURATION = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION))
//                    val TYPE = cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE))
//                    val NEW = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NEW))
//                    Log.e("asd", "$id $number $DURATION $TYPE $NEW")

                    val id =
                        cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                    val name =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                    val path =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                    val date =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED))
                    Log.e("asd", "$id $name $path $date")

                    imageView.setImageURI(
                        ContentUris.withAppendedId(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            id
                        )
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            cursor.close()
        }
    }
}