package com.example.crudfirebase.appFirebase.ui.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import java.io.ByteArrayOutputStream

object ImageUtils {

    fun uriToBase64(
        context: Context,
        uri: Uri
    ): String {

        val input = context.contentResolver.openInputStream(uri)

        val bitmap = BitmapFactory.decodeStream(input)

        val output = ByteArrayOutputStream()

        bitmap.compress(
            Bitmap.CompressFormat.JPEG,
            60,
            output
        )

        val bytes = output.toByteArray()

        return Base64.encodeToString(
            bytes,
            Base64.DEFAULT
        )
    }
}