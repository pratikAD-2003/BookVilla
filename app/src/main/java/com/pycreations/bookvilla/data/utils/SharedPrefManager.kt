package com.pycreations.bookvilla.data.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object SharedPrefManager {
    private const val MY_SELECTED_GENRE = "djksdnsjfmnsdnfjamnfsdnj"
    private const val MY_SELECTED_GENRE_KEY = "djksdnsjfmnsdnfjamnfsdnjfkjdsfn"
    private const val DARK_MODE = "dkfhaskljfadsfhafnfd"
    private const val DARK_MODE_KEY = "ugtyftyugahsdfaufhad"

    private const val SAVED_BOOKS = "dfvghbnjmklliuyt"
    private const val SAVED_BOOKS_KEY = "uhijklauyhgtfrvghb"

    fun updateGenre(context: Context, genre: String): String {
        val sharedPref = context.getSharedPreferences(MY_SELECTED_GENRE, MODE_PRIVATE)
        sharedPref.edit {
            putString(MY_SELECTED_GENRE_KEY, genre);
        }
        return getGenre(context)
    }

    fun getGenre(context: Context): String {
        val sharedPref = context.getSharedPreferences(MY_SELECTED_GENRE, MODE_PRIVATE)
        return sharedPref.getString(MY_SELECTED_GENRE_KEY, "Fiction") ?: "Fiction"
    }

    fun getSavedBookList(context: Context): List<String> {
        val prefs = context.getSharedPreferences(SAVED_BOOKS, Context.MODE_PRIVATE)
        val json = prefs.getString(SAVED_BOOKS_KEY, null) ?: return emptyList()
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(json, type)
    }

    fun saveBookList(context: Context, list: List<String>) {
        val prefs = context.getSharedPreferences(SAVED_BOOKS, Context.MODE_PRIVATE)
        val json = Gson().toJson(list)
        prefs.edit { putString(SAVED_BOOKS_KEY, json) }
    }

    fun addBookToList(context: Context, book: String) {
        val currentList = getSavedBookList(context).toMutableList()
        if (!currentList.contains(book)) {
            currentList.add(book)
            saveBookList(context, currentList)
        }
    }

    fun removeBookFromList(context: Context, book: String) {
        val currentList = getSavedBookList(context).toMutableList()
        if (currentList.remove(book)) {
            saveBookList(context, currentList)
        }
    }

    fun isBookSaved(context: Context, book: String): Boolean {
        return getSavedBookList(context).contains(book)
    }
}