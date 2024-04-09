package com.example.weis.viewModel

import androidx.lifecycle.ViewModel
import com.example.weis.modals.User
import com.google.firebase.firestore.DocumentSnapshot

class UserViewModel: ViewModel() {

    fun convertToUserModel(document: DocumentSnapshot): User {
        val id = document.id
        val name = document.getString("name")
        val email = document.id
        val password = document.getString("password") ?: ""
        val picture = document.getString("picture") ?: ""
        val hrsOfFocus :Long? = document.get("hrsOfFocus") as Long
        val tasksDone: Long? = document.get("tasksDone") as Long

        return User(name = name, email = email, password = password, picture = picture, hrsOfFocus = hrsOfFocus, tasksDone = tasksDone)
    }
}