package com.example.weis.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.weis.R
import com.example.weis.ui.activity.MainContainerActivity

class NotificationService (
    private val context: Context
){
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


    fun showNotification(time: String){
        val activityIntent = Intent(context, MainContainerActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_weis)
            .setContentTitle("Goal Reminder")
            .setContentText("Its $time, time for your goal")
            .setContentIntent(activityPendingIntent)
            .build()

         notificationManager.notify(1, notification)
    }

    companion object{
        const val CHANNEL_ID = "channel_id"
    }
}