package com.example.weis.presentation

import com.example.weis.modals.User

data class SignInResult(
    val data : User?,
    val errorMsg : String?
)
