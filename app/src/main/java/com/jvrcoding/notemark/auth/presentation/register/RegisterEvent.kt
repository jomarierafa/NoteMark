package com.jvrcoding.notemark.auth.presentation.register

import com.jvrcoding.notemark.core.presentation.util.UiText

sealed interface RegisterEvent {
    data object RegistrationSuccess: RegisterEvent
    data class Error(val error: UiText): RegisterEvent
}