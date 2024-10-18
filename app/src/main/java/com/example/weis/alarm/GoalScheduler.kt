package com.example.weis.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.weis.modals.GoalNotification
import java.time.ZoneId

class GoalScheduler(
	private val context: Context
) {
	
	private val alarmManager = context.getSystemService(AlarmManager::class.java)
	
	fun schedule(goalNotification: GoalNotification){
		val intent = Intent(context, AlarmReceiver::class.java).apply {
			putExtra("EXTRA_MESSAGE", goalNotification.message)
		}
		alarmManager.setExact(
			AlarmManager.RTC_WAKEUP,
			goalNotification.time.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
			PendingIntent.getBroadcast(
				context,
				goalNotification.hashCode(),
				intent,
				PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
			)
		)
	}
	
	fun cancelAlarm(goalNotification: GoalNotification){
		alarmManager.cancel(
			PendingIntent.getBroadcast(
				context,
				goalNotification.hashCode(),
				Intent(context, AlarmReceiver::class.java),
				PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
			)
		)
	}
}