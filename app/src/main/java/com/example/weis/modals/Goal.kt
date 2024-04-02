package com.example.weis.modals

data class Goal(
    var id: String? = null,
    val goal: String,
    val duration: Long,
    val date: String,
    val time: String,
    var open: Boolean = false
)