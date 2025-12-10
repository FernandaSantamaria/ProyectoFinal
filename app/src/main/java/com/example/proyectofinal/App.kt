package com.example.proyectofinal

import android.app.Application
import com.example.proyectofinal.domain.utils.Preferences

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        Preferences.init(this)
    }
}