package com.example.projet_couvoiturage.domain.usecase

import com.example.projet_couvoiturage.data.local.entity.ReservationEntity
import com.example.projet_couvoiturage.domain.repository.ReservationRepository
import com.example.projet_couvoiturage.domain.repository.TripRepository
import javax.inject.Inject

class BookTripUseCase @Inject constructor(
    private val reservationRepository: ReservationRepository,
    private val tripRepository: TripRepository
) {
    suspend operator fun invoke(tripId: Long, passengerId: Long): Boolean {
        val trip = tripRepository.getTripByIdSuspend(tripId) ?: return false
        if (trip.seatsAvailable > 0) {
            val reservation = ReservationEntity(
                tripId = tripId,
                passengerId = passengerId,
                status = "CONFIRMED"
            )
            reservationRepository.createReservation(reservation)
            
            val updatedTrip = trip.copy(seatsAvailable = trip.seatsAvailable - 1)
            tripRepository.updateTrip(updatedTrip)
            return true
        }
        return false
    }
}
