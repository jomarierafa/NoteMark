package com.jvrcoding.notemark.auth.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jvrcoding.notemark.R
import com.jvrcoding.notemark.auth.domain.AuthRepository
import com.jvrcoding.notemark.auth.domain.UserDataValidator
import com.jvrcoding.notemark.core.domain.util.DataError
import com.jvrcoding.notemark.core.domain.util.Result
import com.jvrcoding.notemark.core.presentation.util.UiText
import com.jvrcoding.notemark.core.presentation.util.asUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userDataValidator: UserDataValidator,
    private val repository: AuthRepository
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
                val isPasswordMatch = userDataValidator.validateConfirmPassword(password, state.confirmPassword)
                state = state.copy(
                    passwordValidationState = passwordValidationState,
                    isConfirmPasswordValid = isPasswordMatch,
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
                            && isPasswordMatch
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
            val result = repository.register(
                username = state.username.trim(),
                email = state.email.trim(),
                password = state.password
            )
            state = state.copy(isRegistering = false)

            when(result) {
                is Result.Error -> {
                    if(result.error == DataError.Network.CONFLICT) {
                        eventChannel.send(RegisterEvent.Error(
                            UiText.StringResource(R.string.error_email_exists)
                        ))
                    } else {
                        eventChannel.send(RegisterEvent.Error(result.error.asUiText()))
                    }
                }
                is Result.Success -> {
                    eventChannel.send(RegisterEvent.RegistrationSuccess)
                }
            }
        }
    }
}