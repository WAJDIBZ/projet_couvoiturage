package com.example.projet_couvoiturage.domain.repository

import com.example.projet_couvoiturage.data.local.entity.TripEntity
import kotlinx.coroutines.flow.Flow

interface TripRepository {
    fun getAllTrips(): Flow<List<TripEntity>>
    fun searchTrips(departureCity: String?, arrivalCity: String?, date: String?): Flow<List<TripEntity>>
    fun getTripById(id: Long): Flow<TripEntity>
    suspend fun getTripByIdSuspend(id: Long): TripEntity?
    suspend fun updateTrip(trip: TripEntity)
    suspend fun insertTrip(trip: TripEntity)
}
