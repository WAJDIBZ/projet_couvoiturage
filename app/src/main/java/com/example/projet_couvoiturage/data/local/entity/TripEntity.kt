package com.example.projet_couvoiturage.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "trips",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["driverId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["driverId"])]
)
data class TripEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val driverId: Long,
    val departureCity: String,
    val departureLat: Double,
    val departureLng: Double,
    val arrivalCity: String,
    val arrivalLat: Double,
    val arrivalLng: Double,
    val date: String, // YYYY-MM-DD
    val departureTime: String, // HH:mm
    val seatsTotal: Int,
    val seatsAvailable: Int,
    val pricePerSeat: Double,
    val notes: String
)
