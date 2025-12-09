package com.example.proyectofinal.data.network

import android.content.Context
import android.content.SharedPreferences

object TokenManager {
    private const val PREF_NAME = "instagram_prefs"
    private const val KEY_TOKEN = "auth_token"
    private const val KEY_USER_ID = "user_id"

    private var sharedPreferences: SharedPreferences? = null

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveToken(token: String) {
        sharedPreferences?.edit()?.putString(KEY_TOKEN, token)?.apply()
    }

    fun getToken(): String? {
        return sharedPreferences?.getString(KEY_TOKEN, null)
    }

    fun saveUserId(userId: Int) {
        sharedPreferences?.edit()?.putInt(KEY_USER_ID, userId)?.apply()
    }

    fun getUserId(): Int {
        return sharedPreferences?.getInt(KEY_USER_ID, -1) ?: -1
    }

    fun clearToken() {
        sharedPreferences?.edit()?.remove(KEY_TOKEN)?.apply()
        sharedPreferences?.edit()?.remove(KEY_USER_ID)?.apply()
    }

    fun isLoggedIn(): Boolean {
        return getToken() != null
    }
}