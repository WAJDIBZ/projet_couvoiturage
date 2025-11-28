package com.example.projet_couvoiturage.domain.usecase

import com.example.projet_couvoiturage.data.local.entity.TripEntity
import com.example.projet_couvoiturage.domain.repository.TripRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTripDetailUseCase @Inject constructor(
    private val tripRepository: TripRepository
) {
    operator fun invoke(id: Long): Flow<TripEntity> {
        return tripRepository.getTripById(id)
    }
}
