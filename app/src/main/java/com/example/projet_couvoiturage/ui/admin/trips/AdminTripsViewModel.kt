package com.example.projet_couvoiturage.ui.admin.trips

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.projet_couvoiturage.data.local.entity.TripEntity
import com.example.projet_couvoiturage.domain.usecase.CancelTripUseCase
import com.example.projet_couvoiturage.domain.usecase.SearchTripsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminTripsViewModel @Inject constructor(
    private val searchTripsUseCase: SearchTripsUseCase,
    private val cancelTripUseCase: CancelTripUseCase
) : ViewModel() {

    val trips: LiveData<List<TripEntity>> = searchTripsUseCase(null, null, null).asLiveData()

    fun cancelTrip(tripId: Long) {
        viewModelScope.launch {
            cancelTripUseCase(tripId)
        }
    }
}
