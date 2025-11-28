package com.example.projet_couvoiturage.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alerts")
data class AlertEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val type: String, // TRIP_CANCELLED, ANOMALY
    val message: String,
    val createdAt: Long,
    val isRead: Boolean
)
