package com.jvrcoding.notemark.auth.presentation.register

sealed interface RegisterAction {
    data object OnLoginClick: RegisterAction
    data object OnRegisterClick: RegisterAction
}