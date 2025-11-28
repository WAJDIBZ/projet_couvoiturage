package com.example.projet_couvoiturage.domain.usecase

import com.example.projet_couvoiturage.domain.repository.ReservationRepository
import javax.inject.Inject

class GetReservationCountUseCase @Inject constructor(
    private val reservationRepository: ReservationRepository
) {
    suspend operator fun invoke(): Int {
        return reservationRepository.getReservationCount()
    }
}
