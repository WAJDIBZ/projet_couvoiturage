package com.example.projet_couvoiturage.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.projet_couvoiturage.data.local.entity.TripEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TripDao {
    @Query("SELECT * FROM trips ORDER BY date ASC, departureTime ASC")
    fun getAllTrips(): Flow<List<TripEntity>>

    @Query("SELECT * FROM trips WHERE id = :id")
    fun getTripById(id: Long): Flow<TripEntity>
    
    @Query("SELECT * FROM trips WHERE id = :id")
    suspend fun getTripByIdSuspend(id: Long): TripEntity?

    @Query("SELECT * FROM trips WHERE driverId = :driverId ORDER BY date DESC, departureTime DESC")
    suspend fun listByDriverId(driverId: Long): List<TripEntity>

    @Query("SELECT * FROM trips WHERE id = :tripId LIMIT 1")
    suspend fun getTripByIdOnce(tripId: Long): TripEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrip(trip: TripEntity)

    @Update
    suspend fun updateTrip(trip: TripEntity)

    @Query("""
        SELECT * FROM trips 
        WHERE (:departureCity IS NULL OR departureCity LIKE '%' || :departureCity || '%')
        AND (:arrivalCity IS NULL OR arrivalCity LIKE '%' || :arrivalCity || '%')
        AND (:date IS NULL OR date = :date)
        ORDER BY date ASC, departureTime ASC
    """)
    fun searchTrips(departureCity: String?, arrivalCity: String?, date: String?): Flow<List<TripEntity>>
}
