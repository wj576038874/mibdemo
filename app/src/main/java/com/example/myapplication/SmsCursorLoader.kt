package com.example.myapplication

import android.content.Context
import android.database.Cursor
import android.database.MatrixCursor
import android.database.MergeCursor
import android.net.Uri
import android.os.Bundle
import androidx.loader.content.CursorLoader

class SmsCursorLoader private constructor(
    context: Context,
    uri: Uri,
    projection: Array<String>? = null,
    selection: String? = null,
    selectionArgs: Array<String>? = null,
    sortOrder: String? = null,
    private val args: Bundle? = null
) : CursorLoader(context, uri, projection, selection, selectionArgs, sortOrder) {


    companion object {
        private val PROJECTION = arrayOf("_id", "address", "person", "body", "date", "type")
        private val URI = Uri.parse("content://sms/")
        private val SELECTION = null
        private val SELECTION_ARGS = null
        private const val SORT_ORDER = "date asc limit 100"
        fun newInstance(context: Context, args: Bundle? = null): CursorLoader {
            return SmsCursorLoader(
                context,
                URI,
                PROJECTION,
                SELECTION,
                SELECTION_ARGS,
                SORT_ORDER,
                args
            )
        }
    }

    override fun loadInBackground(): Cursor {
        val cursor = super.loadInBackground()
        val matrixCursor = MatrixCursor(PROJECTION)
        matrixCursor.addRow(
            arrayOf(
                -1L, "110", "xxx", args?.getString("body"), "123", "456"
            )
        )
        return MergeCursor(arrayOf(matrixCursor, cursor))
    }

}