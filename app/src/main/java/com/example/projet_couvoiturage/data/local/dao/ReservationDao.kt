package com.example.projet_couvoiturage.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.projet_couvoiturage.data.local.entity.ReservationEntity
import com.example.projet_couvoiturage.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReservationDao {
    @Query("SELECT * FROM reservations WHERE passengerId = :userId")
    fun getReservationsForUser(userId: Long): Flow<List<ReservationEntity>>

    @Query("SELECT * FROM reservations WHERE tripId = :tripId")
    fun getReservationsForTrip(tripId: Long): Flow<List<ReservationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReservation(reservation: ReservationEntity)

    @Update
    suspend fun updateReservation(reservation: ReservationEntity)
    
    @Query("SELECT COUNT(*) FROM reservations")
    suspend fun getReservationCount(): Int

    @Query(
        """
        SELECT u.* FROM reservations r
        JOIN users u ON u.id = r.passengerId
        WHERE r.tripId = :tripId
        ORDER BY u.fullName ASC
        """
    )
    suspend fun passengersForTrip(tripId: Long): List<UserEntity>
}
