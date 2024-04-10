package com.example.weis.modals

data class User(
    var name: String?,
    val email: String,
    val password: String,
    var picture: String?,
    var secOfFocus: Long? = 0,
    var tasksDone: Long? = 0
)