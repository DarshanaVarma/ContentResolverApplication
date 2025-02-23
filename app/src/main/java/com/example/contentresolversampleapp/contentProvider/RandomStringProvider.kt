package com.example.contentresolversampleapp.contentProvider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import java.text.SimpleDateFormat
import java.util.*

class RandomStringProvider : ContentProvider() {

    // Define constants for the provider
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
        // The selectionArgs will contain the length of the random string to generate
        val length = selectionArgs?.get(0)?.toIntOrNull() ?: return null
        val randomString = generateRandomString(length)
        val currentTime = getCurrentTime()

        // Prepare the data to return: random string, length, and created date/time
        val matrixCursor = MatrixCursor(arrayOf("random_string", "length", "created_at"))
        matrixCursor.addRow(arrayOf(randomString, length.toString(), currentTime))

        return matrixCursor
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        // Not needed for this example, return null
        return null
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        // Not needed for this example, return 0
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        // Not needed for this example, return 0
        return 0
    }

    override fun getType(uri: Uri): String? {
        // Return MIME type (not needed for this case)
        return "vnd.android.cursor.item/vnd.$AUTHORITY.random_string"
    }

    // Function to generate the random string
    private fun generateRandomString(length: Int): String {
        val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        return (1..length)
            .map { characters.random() }
            .joinToString("")
    }

    // Function to get the current date/time in a specific format
    private fun getCurrentTime(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }
}
