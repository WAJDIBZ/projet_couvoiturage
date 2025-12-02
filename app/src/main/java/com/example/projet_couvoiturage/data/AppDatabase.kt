package com.example.projet_couvoiturage.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.projet_couvoiturage.data.dao.ConducteurDao
import com.example.projet_couvoiturage.data.dao.TrajectDao
import com.example.projet_couvoiturage.data.entity.Conducteur
import com.example.projet_couvoiturage.data.entity.Traject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Conducteur::class, Traject::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun conducteurDao(): ConducteurDao
    abstract fun trajectDao(): TrajectDao

    companion object {
        private const val DB_NAME = "covoiturage.db"
        private const val PREPOP_EMAIL = "driver@example.com"
        private const val PREPOP_ADDRESS = "123 Rue Exemple, Paris"
        private const val PREPOP_NAME = "Driver One"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun get(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, DB_NAME
                )
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val appContext = context.applicationContext
                            CoroutineScope(Dispatchers.IO).launch {
                                val dao = get(appContext).conducteurDao()
                                val conducteur = Conducteur(
                                    email = PREPOP_EMAIL,
                                    address = PREPOP_ADDRESS,
                                    name = PREPOP_NAME
                                )
                                dao.insertOrIgnore(conducteur)
                            }
                        }
                    })
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
