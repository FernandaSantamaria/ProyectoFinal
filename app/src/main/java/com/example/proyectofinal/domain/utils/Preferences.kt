package com.example.proyectofinal.domain.utils

import android.content.Context
import android.content.SharedPreferences

object Preferences {
    private lateinit var settings: SharedPreferences

    fun init(context: Context) {
        settings =
            context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    }

    fun saveUserId(userId: Int) {
        settings.edit().putInt("userId", userId).apply()
    }

    fun getUserId(): Int {
        return settings.getInt("userId", 0)
    }

    fun saveIsLogged(isLogged: Boolean) {
        settings.edit().putBoolean("isLogged", isLogged).apply()
    }

    fun getIsLogged(): Boolean {
        return settings.getBoolean("isLogged", false)
    }

    fun clearSettings() {
        settings.edit().clear().apply()
    }
}