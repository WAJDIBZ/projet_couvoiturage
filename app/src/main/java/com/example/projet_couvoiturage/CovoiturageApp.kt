package com.example.projet_couvoiturage

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CovoiturageApp : Application() {
    override fun onCreate() {
        super.onCreate()
        com.example.projet_couvoiturage.auth.SessionManager.init(this)
    }
}
