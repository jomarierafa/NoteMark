package com.jvrcoding.notemark.auth.presentation.login

sealed interface LoginAction {
    data class OnEmailChanged(val value: String) : LoginAction
    data class OnPasswordChanged(val value: String) : LoginAction
    data object OnLoginClick: LoginAction
    data object OnRegisterClick: LoginAction
}