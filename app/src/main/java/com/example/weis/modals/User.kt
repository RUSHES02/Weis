package com.example.weis.modals

data class User(
    var name: String?,
    val email: String,
    val password: String,
    var picture: String?,
    var hrsOfFocus: Long? = 0,
    var tasksDone: Long? = 0
)