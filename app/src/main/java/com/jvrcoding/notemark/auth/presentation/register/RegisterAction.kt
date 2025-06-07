package com.jvrcoding.notemark.auth.presentation.register

sealed interface RegisterAction {
    data class OnUsernameChanged(val value: String) : RegisterAction
    data class OnEmailChanged(val value: String) : RegisterAction
    data class OnPasswordChanged(val value: String) : RegisterAction
    data class OnConfirmPasswordChanged(val value: String): RegisterAction
    data object OnLoginClick: RegisterAction
    data object OnRegisterClick: RegisterAction
}