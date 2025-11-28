package com.example.projet_couvoiturage.domain.usecase

import com.example.projet_couvoiturage.domain.repository.AlertRepository
import javax.inject.Inject

class MarkAlertReadUseCase @Inject constructor(
    private val alertRepository: AlertRepository
) {
    suspend operator fun invoke(id: Long) {
        alertRepository.markAsRead(id)
    }
}
