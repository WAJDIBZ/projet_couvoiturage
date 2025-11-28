package com.example.projet_couvoiturage.data.repository

import com.example.projet_couvoiturage.data.local.dao.TripDao
import com.example.projet_couvoiturage.data.local.entity.TripEntity
import com.example.projet_couvoiturage.domain.repository.TripRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TripRepositoryImpl @Inject constructor(
    private val tripDao: TripDao
) : TripRepository {
    override fun getAllTrips(): Flow<List<TripEntity>> {
        return tripDao.getAllTrips()
    }

    override fun searchTrips(
        departureCity: String?,
        arrivalCity: String?,
        date: String?
    ): Flow<List<TripEntity>> {
        return tripDao.searchTrips(departureCity, arrivalCity, date)
    }

    override fun getTripById(id: Long): Flow<TripEntity> {
        return tripDao.getTripById(id)
    }

    override suspend fun getTripByIdSuspend(id: Long): TripEntity? {
        return tripDao.getTripByIdSuspend(id)
    }

    override suspend fun updateTrip(trip: TripEntity) {
        tripDao.updateTrip(trip)
    }

    override suspend fun insertTrip(trip: TripEntity) {
        tripDao.insertTrip(trip)
    }
}
