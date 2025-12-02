package com.example.projet_couvoiturage.data.repo

import com.example.projet_couvoiturage.data.AppDatabase
import com.example.projet_couvoiturage.data.entity.Traject

class TrajectRepository(private val db: AppDatabase) {
    suspend fun addTraject(t: Traject) = db.trajectDao().insert(t)
}
