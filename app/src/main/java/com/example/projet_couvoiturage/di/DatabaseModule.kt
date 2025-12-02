package com.example.projet_couvoiturage.di

import android.content.Context
import com.example.projet_couvoiturage.data.local.AppDatabase
import com.example.projet_couvoiturage.data.dao.ConducteurDao
import com.example.projet_couvoiturage.data.dao.TrajectDao
import com.example.projet_couvoiturage.data.local.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideCoroutineScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob())
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        scope: CoroutineScope
    ): AppDatabase {
        return AppDatabase.getDatabase(context, scope)
    }

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao = database.userDao()

    @Provides
    fun provideTripDao(database: AppDatabase): TripDao = database.tripDao()

    @Provides
    fun provideReservationDao(database: AppDatabase): ReservationDao = database.reservationDao()

    @Provides
    fun provideChatDao(database: AppDatabase): ChatDao = database.chatDao()

    @Provides
    fun provideAlertDao(database: AppDatabase): AlertDao = database.alertDao()

    @Provides
    fun provideConducteurDao(database: AppDatabase): ConducteurDao = database.conducteurDao()

    @Provides
    fun provideTrajectDao(database: AppDatabase): TrajectDao = database.trajectDao()

    @Provides
    fun providePlaceDao(database: AppDatabase): PlaceDao = database.placeDao()
}
