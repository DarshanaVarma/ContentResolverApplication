package com.example.contentresolversampleapp.model

import android.content.Context
import android.net.Uri

class RandomStringRepository(private val context: Context)  {
        lateinit var randomStringObject: RandomString
    fun getRandomString(length: Int) : RandomString{
        val contentUri = Uri.parse("content://com.example.randomstringprovider/random_string")
        val cursor = context.contentResolver.query(
            contentUri,
            null,          // No specific columns are needed, we just need the string and other data
            null,          // No selection filter
            arrayOf(length.toString()), // Pass the length as selection argument
            null           // No sorting
        )

        cursor?.let {
            if (it.moveToFirst()) {
                val randomString = it.getString(it.getColumnIndex("random_string"))
                val stringLength = it.getString(it.getColumnIndex("length"))
                val createdAt = it.getString(it.getColumnIndex("created_at"))
          randomStringObject = RandomString(randomString,stringLength.toInt(),createdAt)
            }
            it.close()
        }
         return randomStringObject
    }

    fun deleteAllGeneratedStrings() {
        val uri = Uri.parse("content://com.iav.contestdataprovider/text")
        context.contentResolver.delete(uri, null, null)
    }

    fun deleteSingleGeneratedString(id: String) {
        val uri = Uri.parse("content://com.iav.contestdataprovider/text/$id")
        context.contentResolver.delete(uri, null, null)
    }
}
