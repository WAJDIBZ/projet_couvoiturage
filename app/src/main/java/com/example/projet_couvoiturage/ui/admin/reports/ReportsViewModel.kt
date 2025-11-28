package com.example.projet_couvoiturage.ui.admin.reports

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projet_couvoiturage.domain.repository.ReservationRepository
import com.example.projet_couvoiturage.domain.repository.TripRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ReportData(
    val totalTrips: Int,
    val totalReservations: Int,
    val occupancyRate: Float,
    val tripsByRoute: Map<String, Int>
)

@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val tripRepository: TripRepository,
    private val reservationRepository: ReservationRepository
) : ViewModel() {

    private val _reportData = MutableLiveData<ReportData>()
    val reportData: LiveData<ReportData> = _reportData

    fun loadReports() {
        viewModelScope.launch {
            val trips = tripRepository.getAllTrips().first()
            val reservationsCount = reservationRepository.getReservationCount()
            
            val totalTrips = trips.size
            val totalSeats = trips.sumOf { it.seatsTotal }
            val availableSeats = trips.sumOf { it.seatsAvailable }
            val reservedSeats = totalSeats - availableSeats
            
            val occupancyRate = if (totalSeats > 0) (reservedSeats.toFloat() / totalSeats.toFloat()) * 100f else 0f
            
            val tripsByRoute = trips.groupBy { "${it.departureCity} -> ${it.arrivalCity}" }
                .mapValues { it.value.size }

            _reportData.value = ReportData(
                totalTrips = totalTrips,
                totalReservations = reservationsCount,
                occupancyRate = occupancyRate,
                tripsByRoute = tripsByRoute
            )
        }
    }
}
