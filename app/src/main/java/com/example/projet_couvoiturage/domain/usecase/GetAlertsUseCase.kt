package com.example.projet_couvoiturage.domain.usecase

import com.example.projet_couvoiturage.data.local.entity.AlertEntity
import com.example.projet_couvoiturage.domain.repository.AlertRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAlertsUseCase @Inject constructor(
    private val alertRepository: AlertRepository
) {
    operator fun invoke(): Flow<List<AlertEntity>> {
        return alertRepository.getAllAlerts()
    }
}
