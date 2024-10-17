package com.example.weis

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.example.weis.utils.NotificationService

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel(){
        val channel = NotificationChannel(
            NotificationService.CHANNEL_ID,
            "Reminders",
            NotificationManager.IMPORTANCE_HIGH
        )
        channel.description = "Used for giving goals reminder"

//        notificationManager.createNotificationChannel(channel)
    }
}