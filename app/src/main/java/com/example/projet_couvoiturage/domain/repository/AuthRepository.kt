package com.example.projet_couvoiturage.domain.repository

import com.example.projet_couvoiturage.data.local.entity.UserEntity

interface AuthRepository {
    suspend fun login(email: String, password: String): UserEntity?
    suspend fun getUserById(id: Long): UserEntity?
}
