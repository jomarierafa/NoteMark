package com.jvrcoding.notemark.auth.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jvrcoding.notemark.auth.domain.UserDataValidator
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userDataValidator: UserDataValidator
): ViewModel() {

    var state by mutableStateOf(RegisterState())
        private set

    private val eventChannel = Channel<RegisterEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        snapshotFlow { state.username }
            .onEach { username ->
                val isUsernameValid = userDataValidator.isValidUsername(username)
                state = state.copy(
                    isUsernameValid = isUsernameValid,
                    canRegister = isUsernameValid && state.isUsernameValid
                            && state.passwordValidationState.isValidPassword
                            && state.isConfirmPasswordValid
                            && !state.isRegistering
                )
            }
            .launchIn(viewModelScope)

        snapshotFlow { state.email }
            .onEach { email ->
                val isValidEmail = userDataValidator.isValidEmail(email)
                state = state.copy(
                    isEmailValid = isValidEmail,
                    canRegister = isValidEmail && state.isUsernameValid
                            && state.passwordValidationState.isValidPassword
                            && state.isConfirmPasswordValid
                            && !state.isRegistering
                )
            }
            .launchIn(viewModelScope)

        snapshotFlow { state.password }
            .onEach { password ->
                val passwordValidationState = userDataValidator.validatePassword(password)
                state = state.copy(
                    passwordValidationState = passwordValidationState,
                    canRegister = state.isEmailValid && state.isUsernameValid
                            && passwordValidationState.isValidPassword
                            && state.isConfirmPasswordValid
                            && !state.isRegistering
                )
            }
            .launchIn(viewModelScope)

        snapshotFlow { state.confirmPassword }
            .onEach { confirmPassword ->
                val isPasswordMatch = userDataValidator.validateConfirmPassword(state.password, confirmPassword)
                state = state.copy(
                    isConfirmPasswordValid = isPasswordMatch,
                    canRegister = state.isEmailValid && state.isUsernameValid
                            && state.passwordValidationState.isValidPassword
                            && state.isConfirmPasswordValid
                            && !state.isRegistering
                )
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: RegisterAction) {
        when(action) {
            RegisterAction.OnRegisterClick -> register()
            is RegisterAction.OnUsernameChanged -> state = state.copy(username = action.value)
            is RegisterAction.OnEmailChanged -> state = state.copy(email = action.value)
            is RegisterAction.OnPasswordChanged -> state = state.copy(password = action.value)
            is RegisterAction.OnConfirmPasswordChanged -> state = state.copy(confirmPassword = action.value)
            else -> Unit
        }
    }


    private fun register() {
        viewModelScope.launch {
            state = state.copy(isRegistering = true)
        }
    }
}