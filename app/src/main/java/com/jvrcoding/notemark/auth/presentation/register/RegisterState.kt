package com.jvrcoding.notemark.auth.presentation.register

import com.jvrcoding.notemark.auth.domain.PasswordValidationState

data class RegisterState(
    val username: String = "",
    val isUsernameValid: Boolean = false,
    val usernameErrorMessage: String = "",

    val email: String = "",
    val isEmailValid: Boolean = false,

    val password: String = "",
    val confirmPassword: String = "",
    val isConfirmPasswordValid: Boolean = false,
    val passwordValidationState: PasswordValidationState = PasswordValidationState(),

    val isRegistering: Boolean = false,
    val canRegister: Boolean = false
) {
    val shouldShowUsernameError: Boolean
        get() = username.isNotEmpty() && !isUsernameValid

    val shouldShowEmailError: Boolean
        get() = email.isNotEmpty() && !isEmailValid

    val shouldShowPasswordError: Boolean
        get() = password.isNotEmpty() && !passwordValidationState.isValidPassword

    val shouldShowConfirmPasswordError: Boolean
        get() = confirmPassword.isNotEmpty() && !isConfirmPasswordValid
}

