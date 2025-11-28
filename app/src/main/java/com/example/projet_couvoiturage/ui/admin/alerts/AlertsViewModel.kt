package com.example.projet_couvoiturage.ui.admin.alerts

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.projet_couvoiturage.data.local.entity.AlertEntity
import com.example.projet_couvoiturage.domain.usecase.GetAlertsUseCase
import com.example.projet_couvoiturage.domain.usecase.MarkAlertReadUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlertsViewModel @Inject constructor(
    private val getAlertsUseCase: GetAlertsUseCase,
    private val markAlertReadUseCase: MarkAlertReadUseCase
) : ViewModel() {

    val alerts: LiveData<List<AlertEntity>> = getAlertsUseCase().asLiveData()

    fun markAsRead(id: Long) {
        viewModelScope.launch {
            markAlertReadUseCase(id)
        }
    }
}
