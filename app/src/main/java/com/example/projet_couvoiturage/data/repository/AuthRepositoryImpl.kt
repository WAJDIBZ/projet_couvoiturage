package com.example.projet_couvoiturage.data.repository

import com.example.projet_couvoiturage.data.local.dao.UserDao
import com.example.projet_couvoiturage.data.local.entity.UserEntity
import com.example.projet_couvoiturage.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : AuthRepository {
    override suspend fun login(email: String, password: String): UserEntity? {
        val user = userDao.getUserByEmail(email)
        return if (user != null && user.password == password) {
            user
        } else {
            null
        }
    }

    override suspend fun getUserById(id: Long): UserEntity? {
        return userDao.getUserById(id)
    }
}
