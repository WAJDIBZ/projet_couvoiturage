package com.example.projet_couvoiturage.auth

import android.content.Context
import com.example.projet_couvoiturage.auth.SessionManager.Role
import com.example.projet_couvoiturage.data.local.AppDatabase

class AuthRepository(private val context: Context) {

    suspend fun login(email: String, password: String): Role? {
        val db = AppDatabase.get(context)

        val user = db.userDao().authenticateUser(email, password)
        if (user != null) {
            SessionManager.currentEmail = user.email
            SessionManager.currentRole = if (user.isAdmin) Role.ADMIN else Role.USER
            return SessionManager.currentRole
        }

        val cond = db.conducteurDao().authenticate(email, password)
        if (cond != null) {
            SessionManager.currentEmail = cond.email
            SessionManager.currentRole = Role.CONDUCTEUR
            return Role.CONDUCTEUR
        }

        return null
    }
}
