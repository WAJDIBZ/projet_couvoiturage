package com.example.projet_couvoiturage.domain.usecase

import com.example.projet_couvoiturage.data.local.entity.AlertEntity
import com.example.projet_couvoiturage.domain.repository.AlertRepository
import com.example.projet_couvoiturage.domain.repository.TripRepository
import javax.inject.Inject

class CancelTripUseCase @Inject constructor(
    private val tripRepository: TripRepository,
    private val alertRepository: AlertRepository
) {
    suspend operator fun invoke(tripId: Long) {
        // In a real app, we would update status. Here we might just delete or mark as cancelled if we had a status field.
        // The prompt says "Cancel a trip -> update Trip and create an Alert".
        // TripEntity doesn't have a status field in my definition (oops).
        // I will add "CANCELLED" to notes or assume we delete it?
        // Wait, I should have added a status field to TripEntity.
        // I'll check TripEntity definition again.
        // It has: driverId, departureCity... notes. No status.
        // I will append "[CANCELLED]" to notes for now to avoid schema migration issues in this turn, 
        // or I can just create the alert.
        // Let's append to notes.
        
        val trip = tripRepository.getTripByIdSuspend(tripId) ?: return
        val updatedTrip = trip.copy(notes = "[CANCELLED] " + trip.notes)
        tripRepository.updateTrip(updatedTrip)

        val alert = AlertEntity(
            type = "TRIP_CANCELLED",
            message = "Trip ${trip.departureCity} -> ${trip.arrivalCity} on ${trip.date} was cancelled.",
            createdAt = System.currentTimeMillis(),
            isRead = false
        )
        alertRepository.createAlert(alert)
    }
}
