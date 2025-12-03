package com.example.projet_couvoiturage.auth

object SessionManager {
    private const val PREF_NAME = "covoiturage_session"
    private const val KEY_EMAIL = "email"
    private const val KEY_ROLE = "role"

    enum class Role { ADMIN, USER, CONDUCTEUR }

    private lateinit var prefs: android.content.SharedPreferences

    private const val KEY_USER_ID = "user_id"

    var currentEmail: String?
        get() = prefs.getString(KEY_EMAIL, null)
        set(value) {
            prefs.edit().putString(KEY_EMAIL, value).apply()
        }

    var currentUserId: Long?
        get() {
            val id = prefs.getLong(KEY_USER_ID, -1L)
            return if (id == -1L) null else id
        }
        set(value) {
            if (value == null) {
                prefs.edit().remove(KEY_USER_ID).apply()
            } else {
                prefs.edit().putLong(KEY_USER_ID, value).apply()
            }
        }

    var currentRole: Role?
        get() {
            val roleStr = prefs.getString(KEY_ROLE, null) ?: return null
            return try {
                Role.valueOf(roleStr)
            } catch (e: Exception) {
                null
            }
        }
        set(value) {
            prefs.edit().putString(KEY_ROLE, value?.name).apply()
        }

    fun init(context: android.content.Context) {
        prefs = context.getSharedPreferences(PREF_NAME, android.content.Context.MODE_PRIVATE)
    }

    fun isLoggedIn(): Boolean {
        return currentEmail != null && currentRole != null
    }

    fun logout() {
        prefs.edit().clear().apply()
    }
}
