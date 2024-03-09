package com.example.weis.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onSignInState(result: SignInResult){
        _state.update { it.copy(
            isSignInSuccesful = result.data != null,
            signInError = result.errorMsg
        ) }
    }

    fun resetState(){
        _state.update { SignInState() }
    }
}