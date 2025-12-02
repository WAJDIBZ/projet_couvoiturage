package com.example.projet_couvoiturage.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.projet_couvoiturage.data.entity.Conducteur

@Dao
interface ConducteurDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(conducteur: Conducteur): Long

    @Query("SELECT * FROM conducteurs WHERE email = :email LIMIT 1")
    suspend fun getByEmail(email: String): Conducteur?

    @Query("SELECT * FROM conducteurs WHERE email = :email AND password = :password LIMIT 1")
    suspend fun authenticate(email: String, password: String): Conducteur?
}
