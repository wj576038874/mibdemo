package com.example.myapplication

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.loader.content.CursorLoader

class MediaCursorLoader private constructor(
    context: Context,
    uri: Uri,
    projection: Array<String>? = null,
    selection: String? = null,
    selectionArgs: Array<String>? = null,
    sortOrder: String? = null,
    private val args: Bundle? = null
) : CursorLoader(context, uri, projection, selection, selectionArgs, sortOrder) {


    companion object {

        fun newInstance(context: Context, args: Bundle? = null): MediaCursorLoader {
            val projection = arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DATE_ADDED
            )
            return MediaCursorLoader(
                context,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                "${MediaStore.Images.Media.DATE_ADDED} >? AND ${MediaStore.Images.Media.DATE_ADDED} <?",
                arrayOf(
                    (System.currentTimeMillis() / 1000 - 200).toString(),
                    (System.currentTimeMillis() / 1000 + 200).toString()
                ),
                MediaStore.Images.Media.DATE_ADDED + " DESC"
            )
        }
    }

    override fun loadInBackground(): Cursor? {
        return super.loadInBackground()
    }
}