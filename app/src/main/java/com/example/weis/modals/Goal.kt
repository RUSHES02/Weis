package com.example.weis.modals

import java.sql.Time
import java.util.Date

data class Goal(
    var id : String? = null,
    val goal : String,
    val duration : String,
    val date : String,
    val time : String,
    var open : Boolean = false
)