package com.example.projet_couvoiturage.ui.traject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.projet_couvoiturage.data.AppDatabase
import com.example.projet_couvoiturage.data.entity.Traject
import com.example.projet_couvoiturage.data.repo.TrajectRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddTrajectViewModel(db: AppDatabase) : ViewModel() {
    private val repo = TrajectRepository(db)

    fun add(
        conducteurEmail: String,
        origin: String,
        destination: String,
        dateTimeMillis: Long,
        seats: Int,
        notes: String?
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.addTraject(
                Traject(
                    conducteurEmail = conducteurEmail,
                    origin = origin,
                    destination = destination,
                    dateTime = dateTimeMillis,
                    seats = seats,
                    notes = notes
                )
            )
        }
    }

    companion object {
        fun provideFactory(db: AppDatabase): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(AddTrajectViewModel::class.java)) {
                        return AddTrajectViewModel(db) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
    }
}
