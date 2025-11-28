package com.example.projet_couvoiturage.domain.repository

import com.example.projet_couvoiturage.data.local.entity.ReservationEntity
import kotlinx.coroutines.flow.Flow

interface ReservationRepository {
    fun getReservationsForUser(userId: Long): Flow<List<ReservationEntity>>
    fun getReservationsForTrip(tripId: Long): Flow<List<ReservationEntity>>
    suspend fun createReservation(reservation: ReservationEntity)
    suspend fun updateReservation(reservation: ReservationEntity)
    suspend fun getReservationCount(): Int
}
