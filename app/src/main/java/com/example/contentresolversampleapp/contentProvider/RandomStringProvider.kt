package com.example.contentresolversampleapp.contentProvider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import java.text.SimpleDateFormat
import java.util.*

class RandomStringProvider : ContentProvider() {

    companion object {
        const val AUTHORITY = "com.example.randomstringprovider"
        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/random_string")
    }

    override fun onCreate(): Boolean {
        // Initialize any resources needed for the provider
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        val length = selectionArgs?.get(0)?.toIntOrNull() ?: return null
        val randomString = generateRandomString(length)
        val currentTime = getCurrentTime()

        val matrixCursor = MatrixCursor(arrayOf("random_string", "length", "created_at"))
        matrixCursor.addRow(arrayOf(randomString, length.toString(), currentTime))

        return matrixCursor
    }

    override fun getType(uri: Uri): String? {
        // Return MIME type (not needed for this case)
        return "vnd.android.cursor.item/vnd.$AUTHORITY.random_string"
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    // Function to generate the random string
    private fun generateRandomString(length: Int): String {
        val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        return (1..length)
            .map { characters.random() }
            .joinToString("")
    }

    private fun getCurrentTime(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }
}
