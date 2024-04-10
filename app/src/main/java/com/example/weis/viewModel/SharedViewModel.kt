package com.example.weis.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weis.modals.User

class SharedViewModel : ViewModel() {
    private val _goal = MutableLiveData<User>()
    val newUser: LiveData<User> = _goal

    fun updateUser(newData: User) {
        _goal.value = newData
    }
}