package com.example.projet_couvoiturage.data.repository

import com.example.projet_couvoiturage.data.local.dao.AlertDao
import com.example.projet_couvoiturage.data.local.entity.AlertEntity
import com.example.projet_couvoiturage.domain.repository.AlertRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AlertRepositoryImpl @Inject constructor(
    private val alertDao: AlertDao
) : AlertRepository {
    override fun getAllAlerts(): Flow<List<AlertEntity>> {
        return alertDao.getAllAlerts()
    }

    override suspend fun createAlert(alert: AlertEntity) {
        alertDao.insertAlert(alert)
    }

    override suspend fun markAsRead(id: Long) {
        alertDao.markAsRead(id)
    }
}
