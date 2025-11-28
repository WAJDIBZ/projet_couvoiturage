package com.example.projet_couvoiturage.ui.employee.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.projet_couvoiturage.data.local.entity.TripEntity
import com.example.projet_couvoiturage.domain.usecase.BookTripUseCase
import com.example.projet_couvoiturage.domain.usecase.GetTripDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TripDetailViewModel @Inject constructor(
    private val getTripDetailUseCase: GetTripDetailUseCase,
    private val bookTripUseCase: BookTripUseCase
) : ViewModel() {

    private val _reservationStatus = MutableLiveData<Result<Boolean>>()
    val reservationStatus: LiveData<Result<Boolean>> = _reservationStatus

    fun getTrip(id: Long): LiveData<TripEntity> {
        return getTripDetailUseCase(id).asLiveData()
    }

    fun bookTrip(tripId: Long, passengerId: Long) {
        viewModelScope.launch {
            val success = bookTripUseCase(tripId, passengerId)
            if (success) {
                _reservationStatus.value = Result.success(true)
            } else {
                _reservationStatus.value = Result.failure(Exception("Booking failed"))
            }
        }
    }
}
