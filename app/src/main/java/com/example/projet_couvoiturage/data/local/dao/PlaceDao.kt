package com.example.projet_couvoiturage.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.projet_couvoiturage.data.local.entity.PlaceEntity

@Dao
interface PlaceDao {
    @Query("SELECT * FROM places ORDER BY name ASC")
    suspend fun getAll(): List<PlaceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<PlaceEntity>)
}
