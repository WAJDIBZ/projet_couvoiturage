package com.example.projet_couvoiturage.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.projet_couvoiturage.data.entity.Conducteur

@Dao
interface ConducteurDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnore(conducteur: Conducteur): Long

    @Update
    suspend fun update(conducteur: Conducteur)

    @Query("SELECT * FROM conducteurs WHERE email = :email LIMIT 1")
    suspend fun getByEmail(email: String): Conducteur?

    @Transaction
    suspend fun upsert(conducteur: Conducteur) {
        val result = insertOrIgnore(conducteur)
        if (result == -1L) {
            update(conducteur)
        }
    }
}
