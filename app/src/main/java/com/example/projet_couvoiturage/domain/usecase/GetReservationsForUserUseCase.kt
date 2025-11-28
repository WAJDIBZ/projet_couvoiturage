package com.example.projet_couvoiturage.domain.usecase

import com.example.projet_couvoiturage.data.local.entity.ReservationEntity
import com.example.projet_couvoiturage.domain.repository.ReservationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetReservationsForUserUseCase @Inject constructor(
    private val reservationRepository: ReservationRepository
) {
    operator fun invoke(userId: Long): Flow<List<ReservationEntity>> {
        return reservationRepository.getReservationsForUser(userId)
    }
}
