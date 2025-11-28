package com.example.projet_couvoiturage.ui.employee.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.projet_couvoiturage.utils.NotificationHelper

class TripReminderWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val notificationHelper = NotificationHelper(applicationContext)
        notificationHelper.showNotification(
            2,
            "Trip Reminder",
            "Your trip is coming up soon! Don't forget to be on time."
        )
        return Result.success()
    }
}
