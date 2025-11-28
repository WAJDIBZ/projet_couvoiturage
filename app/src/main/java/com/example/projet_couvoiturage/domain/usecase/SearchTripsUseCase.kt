package com.example.projet_couvoiturage.domain.usecase

import com.example.projet_couvoiturage.data.local.entity.TripEntity
import com.example.projet_couvoiturage.domain.repository.TripRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchTripsUseCase @Inject constructor(
    private val tripRepository: TripRepository
) {
    operator fun invoke(departureCity: String?, arrivalCity: String?, date: String?): Flow<List<TripEntity>> {
        return tripRepository.searchTrips(departureCity, arrivalCity, date)
    }
}
