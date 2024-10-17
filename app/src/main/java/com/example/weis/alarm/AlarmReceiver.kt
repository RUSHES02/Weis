package com.example.weis.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.firebase.logger.Logger

class AlarmReceiver: BroadcastReceiver() {
	override fun onReceive(p0: Context?, intent: Intent?) {
		val message = intent?.getStringExtra("EXTRA_MESSAGE") ?: return
		Log.d("AlarmReceiver", "Alarm triggered: $message")
	}
}