package com.example.weis.modals

import java.time.LocalDateTime

data class GoalNotification(
	val time: LocalDateTime,
	val message: String
)