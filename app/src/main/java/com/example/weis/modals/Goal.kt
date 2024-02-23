package com.example.weis.modals

data class Goal(
    val id : Int? = null,
    val goal : String,
    val duration : String,
    var open : Boolean = false
)