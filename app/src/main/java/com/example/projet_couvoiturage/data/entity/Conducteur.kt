package com.example.projet_couvoiturage.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "conducteurs",
    indices = [Index(value = ["email"], unique = true)]
)
data class Conducteur(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val email: String,
    val password: String,       // PLAINTEXT for local demo
    val address: String,
    val name: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)
