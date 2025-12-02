package com.example.projet_couvoiturage.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.projet_couvoiturage.data.dao.ConducteurDao
import com.example.projet_couvoiturage.data.dao.TrajectDao
import com.example.projet_couvoiturage.data.entity.Conducteur
import com.example.projet_couvoiturage.data.entity.Traject
import com.example.projet_couvoiturage.data.local.dao.AlertDao
import com.example.projet_couvoiturage.data.local.dao.ChatDao
import com.example.projet_couvoiturage.data.local.dao.ReservationDao
import com.example.projet_couvoiturage.data.local.dao.TripDao
import com.example.projet_couvoiturage.data.local.dao.UserDao
import com.example.projet_couvoiturage.data.local.entity.AlertEntity
import com.example.projet_couvoiturage.data.local.entity.ChatMessageEntity
import com.example.projet_couvoiturage.data.local.entity.ReservationEntity
import com.example.projet_couvoiturage.data.local.entity.TripEntity
import com.example.projet_couvoiturage.data.local.entity.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        UserEntity::class,
        TripEntity::class,
        ReservationEntity::class,
        ChatMessageEntity::class,
        AlertEntity::class,
        Conducteur::class,
        Traject::class
    ],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun tripDao(): TripDao
    abstract fun reservationDao(): ReservationDao
    abstract fun chatDao(): ChatDao
    abstract fun alertDao(): AlertDao
    abstract fun conducteurDao(): ConducteurDao
    abstract fun trajectDao(): TrajectDao

    companion object {
        private const val PREPOP_EMAIL = "driver@gmail.com"
        private const val PREPOP_ADDRESS = "123 Rue Exemple, Paris"
        private const val PREPOP_NAME = "Driver One"
        private const val PREPOP_PASSWORD = "driver123"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
        ): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "covoiturage_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(AppDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        fun get(context: Context): AppDatabase = getDatabase(context)
    }

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(
                        database.userDao(),
                        database.tripDao(),
                        database.alertDao(),
                        database.conducteurDao()
                    )
                }
            }
        }

        private suspend fun populateDatabase(
            userDao: UserDao,
            tripDao: TripDao,
            alertDao: AlertDao,
            conducteurDao: ConducteurDao
        ) {
            // Users
            val admin = UserEntity(
                fullName = "Administrateur Covoiturage-Intell",
                email = "admin@gmail.com",
                password = "Admin@2025",
                isAdmin = true
            )
            userDao.insertUser(admin)

            val moukhtar = UserEntity(
                fullName = "Moukhtar ben moustfa",
                email = "moukhtar@gmail.com",
                password = "moukhtar123",
                isAdmin = false
            )
            userDao.insertUser(moukhtar)

            val idriss = UserEntity(
                fullName = "Idriss Somrani",
                email = "idriss@gmail.com",
                password = "idriss123",
                isAdmin = false
            )
            userDao.insertUser(idriss)

            val youssef = UserEntity(
                fullName = "Youssef Khemiri",
                email = "youssef.khemiri@covoiturage-intell.tn",
                password = "youssef123",
                isAdmin = false
            )
            userDao.insertUser(youssef)

            // Trips
            val driverMoukhtar = userDao.getUserByEmail("moukhtar@gmail.com") ?: return
            val driverIdriss = userDao.getUserByEmail("idriss@gmail.com") ?: return
            val driverYoussef = userDao.getUserByEmail("youssef.khemiri@covoiturage-intell.tn") ?: return

            val trips = listOf(
                TripEntity(
                    driverId = driverMoukhtar.id,
                    departureCity = "Tunis", departureLat = 36.8065, departureLng = 10.1815,
                    arrivalCity = "Sousse", arrivalLat = 35.8256, arrivalLng = 10.6369,
                    date = "2025-05-10", departureTime = "08:00",
                    seatsTotal = 4, seatsAvailable = 4, pricePerSeat = 15.0, notes = "Non-smoking"
                ),
                TripEntity(
                    driverId = driverMoukhtar.id,
                    departureCity = "Tunis", departureLat = 36.8065, departureLng = 10.1815,
                    arrivalCity = "Beja", arrivalLat = 36.7256, arrivalLng = 9.1817,
                    date = "2025-05-11", departureTime = "09:00",
                    seatsTotal = 3, seatsAvailable = 3, pricePerSeat = 10.0, notes = "Music allowed"
                ),
                TripEntity(
                    driverId = driverIdriss.id,
                    departureCity = "Beja", departureLat = 36.7256, departureLng = 9.1817,
                    arrivalCity = "Tunis", arrivalLat = 36.8065, arrivalLng = 10.1815,
                    date = "2025-05-12", departureTime = "14:00",
                    seatsTotal = 4, seatsAvailable = 4, pricePerSeat = 20.0, notes = "AC available"
                ),
                TripEntity(
                    driverId = driverIdriss.id,
                    departureCity = "Sfax", departureLat = 34.7406, departureLng = 10.7603,
                    arrivalCity = "Gabès", arrivalLat = 33.8815, arrivalLng = 10.0982,
                    date = "2025-05-13", departureTime = "10:00",
                    seatsTotal = 3, seatsAvailable = 3, pricePerSeat = 18.0, notes = ""
                ),
                TripEntity(
                    driverId = driverYoussef.id,
                    departureCity = "Tunis", departureLat = 36.8065, departureLng = 10.1815,
                    arrivalCity = "Nabeul", arrivalLat = 36.4513, arrivalLng = 10.7353,
                    date = "2025-05-14", departureTime = "17:00",
                    seatsTotal = 4, seatsAvailable = 4, pricePerSeat = 12.0, notes = "Leaving from downtown"
                ),
                TripEntity(
                    driverId = driverYoussef.id,
                    departureCity = "Monastir", departureLat = 35.7770, departureLng = 10.8262,
                    arrivalCity = "Sousse", arrivalLat = 35.8256, arrivalLng = 10.6369,
                    date = "2025-05-15", departureTime = "07:30",
                    seatsTotal = 4, seatsAvailable = 4, pricePerSeat = 5.0, notes = "Daily commute"
                )
            )
            trips.forEach { tripDao.insertTrip(it) }

            // Alerts
            val alert1 = AlertEntity(
                type = "TRIP_CANCELLED",
                message = "Important cancellation: Tunis -> Sousse on 2025-05-10",
                createdAt = System.currentTimeMillis(),
                isRead = false
            )
            val alert2 = AlertEntity(
                type = "ANOMALY",
                message = "Anomaly: driver cancelled 3 trips in a row",
                createdAt = System.currentTimeMillis() - 86400000,
                isRead = false
            )
            alertDao.insertAlert(alert1)
            alertDao.insertAlert(alert2)

            val conducteur = Conducteur(
                email = PREPOP_EMAIL,
                password = PREPOP_PASSWORD,
                address = PREPOP_ADDRESS,
                name = PREPOP_NAME
            )
            conducteurDao.insert(conducteur)
        }
    }
}
