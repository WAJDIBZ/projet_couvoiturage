package com.example.projet_couvoiturage.data.repository

import com.example.projet_couvoiturage.data.local.dao.ReservationDao
import com.example.projet_couvoiturage.data.local.entity.ReservationEntity
import com.example.projet_couvoiturage.domain.repository.ReservationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReservationRepositoryImpl @Inject constructor(
    private val reservationDao: ReservationDao
) : ReservationRepository {
    override fun getReservationsForUser(userId: Long): Flow<List<ReservationEntity>> {
        return reservationDao.getReservationsForUser(userId)
    }

    override fun getReservationsForTrip(tripId: Long): Flow<List<ReservationEntity>> {
        return reservationDao.getReservationsForTrip(tripId)
    }

    override suspend fun createReservation(reservation: ReservationEntity) {
        reservationDao.insertReservation(reservation)
    }

    override suspend fun updateReservation(reservation: ReservationEntity) {
        reservationDao.updateReservation(reservation)
    }

    override suspend fun getReservationCount(): Int {
        return reservationDao.getReservationCount()
    }
}
