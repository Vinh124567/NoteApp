package com.example.noteappwithfirebase.Responsitory

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.example.noteappwithfirebase.Model.User

class SharedPreferencesResponsitory private constructor(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("Login", Context.MODE_PRIVATE)

    companion object {
        private var instance: SharedPreferencesResponsitory? = null
        operator fun invoke(context: Context) = instance ?: synchronized(this) {
            instance ?: SharedPreferencesResponsitory(context.applicationContext).also {
                instance = it
            }
        }

    }
    fun saveLogin(userName: String, passWord: String) {
        val editor = sharedPreferences.edit()
        editor.putString("username", userName)
        editor.putString("password", passWord)
        editor.apply()
    }

    fun getSavedLoginInfo(): User? {
        val username = sharedPreferences.getString("username", null)
        val password = sharedPreferences.getString("password", null)

        return if (username != null && password != null) {
            User(username, password)
        } else {
            null
        }
    }

    fun clearSavedLoginInfo() {
        val editor = sharedPreferences.edit()
        editor.remove("username")
        editor.remove("password")
        editor.apply()
    }


}