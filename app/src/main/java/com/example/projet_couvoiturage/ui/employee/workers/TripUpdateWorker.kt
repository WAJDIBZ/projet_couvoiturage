package com.example.projet_couvoiturage.ui.employee.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.projet_couvoiturage.utils.NotificationHelper

class TripUpdateWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val notificationHelper = NotificationHelper(applicationContext)
        notificationHelper.showNotification(
            3,
            "Trip Update",
            "The driver has updated the trip details. Please check the app."
        )
        return Result.success()
    }
}
