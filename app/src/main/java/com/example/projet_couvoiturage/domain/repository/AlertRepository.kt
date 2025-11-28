package com.example.projet_couvoiturage.domain.repository

import com.example.projet_couvoiturage.data.local.entity.AlertEntity
import kotlinx.coroutines.flow.Flow

interface AlertRepository {
    fun getAllAlerts(): Flow<List<AlertEntity>>
    suspend fun createAlert(alert: AlertEntity)
    suspend fun markAsRead(id: Long)
}
