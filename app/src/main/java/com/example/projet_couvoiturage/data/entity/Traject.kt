package com.example.projet_couvoiturage.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "trajects",
    indices = [Index(value = ["conducteurEmail"])]
)
data class Traject(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val conducteurEmail: String,
    val origin: String,
    val destination: String,
    val dateTime: Long,
    val seats: Int,
    val notes: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)
