package com.example.projet_couvoiturage.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "places")
data class PlaceEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val lat: Double,
    val lng: Double
)
