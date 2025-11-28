package com.example.projet_couvoiturage.domain.usecase

import com.example.projet_couvoiturage.data.local.entity.UserEntity
import com.example.projet_couvoiturage.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): UserEntity? {
        return authRepository.login(email, password)
    }
}
