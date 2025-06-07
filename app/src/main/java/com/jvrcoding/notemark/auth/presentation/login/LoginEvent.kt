package com.jvrcoding.notemark.auth.presentation.login

import com.jvrcoding.notemark.core.presentation.util.UiText


sealed interface LoginEvent {
    data class Error(val error: UiText): LoginEvent
    data object LoginSuccess: LoginEvent
}