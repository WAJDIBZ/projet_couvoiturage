package com.example.projet_couvoiturage.ui.employee.trips

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.projet_couvoiturage.data.local.entity.TripEntity
import com.example.projet_couvoiturage.domain.usecase.SearchTripsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class TripListViewModel @Inject constructor(
    private val searchTripsUseCase: SearchTripsUseCase
) : ViewModel() {

    private val filters = MutableStateFlow(Triple<String?, String?, String?>(null, null, null))

    val trips: LiveData<List<TripEntity>> = filters.flatMapLatest { (dep, arr, date) ->
        searchTripsUseCase(dep, arr, date)
    }.asLiveData()

    fun search(departure: String?, arrival: String?, date: String?) {
        filters.value = Triple(departure, arrival, date)
    }
}
