package com.example.projet_couvoiturage.auth

object SessionManager {
    enum class Role { ADMIN, USER, CONDUCTEUR }
    @Volatile var currentEmail: String? = null
    @Volatile var currentRole: Role? = null
}
