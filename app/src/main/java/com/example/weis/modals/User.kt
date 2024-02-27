package com.example.weis.modals

import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.EditText

data class User(
    val id: Int? = null,
    val name: String,
    val email: String,
    val password: String
)
