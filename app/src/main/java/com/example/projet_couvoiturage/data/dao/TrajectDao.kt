package com.example.projet_couvoiturage.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.projet_couvoiturage.data.entity.Traject
import kotlinx.coroutines.flow.Flow

@Dao
interface TrajectDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(traject: Traject): Long

    @Query("SELECT * FROM trajects WHERE conducteurEmail = :email ORDER BY dateTime DESC")
    fun listByConducteur(email: String): Flow<List<Traject>>

    @Query("DELETE FROM trajects WHERE id = :id")
    suspend fun delete(id: Int)
}
