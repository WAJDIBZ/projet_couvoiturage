package com.example.projet_couvoiturage.ui.admin.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projet_couvoiturage.domain.usecase.GetReservationCountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getReservationCountUseCase: GetReservationCountUseCase
) : ViewModel() {

    private val _reservationCount = MutableLiveData<Int>()
    val reservationCount: LiveData<Int> = _reservationCount

    fun loadStats() {
        viewModelScope.launch {
            _reservationCount.value = getReservationCountUseCase()
        }
    }
}
