package com.jvrcoding.notemark.auth.presentation.login

import androidx.compose.foundation.text.input.TextFieldState

data class LoginState(
    val email: String = "",
    val password: String = "",
    val canLogin: Boolean = false,
    val isLoggingIn: Boolean = false
)