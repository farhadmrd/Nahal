package com.nahal.developer.family.nahal.database.converters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.text.TextUtils
import android.util.Base64
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream
import java.util.Date

object Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromBase64ToBitmap(base64Value: String?): Bitmap? {
        return if (!TextUtils.isEmpty(base64Value)) {
            val decodedBytes = Base64.decode(base64Value, 0)
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        } else {
            null
        }
    }

    @TypeConverter
    fun fromBitmapToBase64(bitmap: Bitmap?): String? {
        return if (bitmap != null) {
            val byteArrayOS = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOS)
            Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT)
        } else {
            null
        }
    }
}