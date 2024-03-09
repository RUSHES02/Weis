package com.example.weis.utils

import android.content.Context
import com.example.weis.modals.User
import com.google.gson.Gson

class StoreUser {

    fun saveData(user: User, context: Context) {

    }

    companion object {
        fun saveData(user: User, context: Context) {
            val sharedPreferences = context.getSharedPreferences("User", Context.MODE_PRIVATE)
            val gson = Gson()
            sharedPreferences.edit().putString("user", gson.toJson(user)).apply()
        }
    }
}