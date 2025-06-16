package com.jvrcoding.notemark.auth.presentation.register

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.jvrcoding.notemark.R
import com.jvrcoding.notemark.auth.presentation.register.layout.RegisterPhoneLandscapeScreen
import com.jvrcoding.notemark.auth.presentation.register.layout.RegisterPhoneScreen
import com.jvrcoding.notemark.auth.presentation.register.layout.RegisterTabletScreen
import com.jvrcoding.notemark.core.presentation.util.DeviceLayoutType
import com.jvrcoding.notemark.core.presentation.util.ObserveAsEvents
import com.jvrcoding.notemark.core.presentation.util.rememberDeviceLayoutType
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreenRoot(
    onLoginClick: () -> Unit,
    onSuccessfulRegistration: () -> Unit,
    viewModel: RegisterViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    ObserveAsEvents(flow = viewModel.events) { event ->
        when(event) {
            is RegisterEvent.Error -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    event.error.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
            RegisterEvent.RegistrationSuccess -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    context.getString(R.string.registration_successful),
                    Toast.LENGTH_LONG
                ).show()
                onSuccessfulRegistration()
            }
        }
    }

    RegisterScreen(
        state = viewModel.state,
        onAction = { action ->
            when(action) {
                RegisterAction.OnLoginClick -> onLoginClick()
                else ->  Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit
) {

    Scaffold(
        modifier = Modifier.fillMaxSize()
      ) { innerPadding ->

        val layoutType = rememberDeviceLayoutType()

        when (layoutType) {
            DeviceLayoutType.PORTRAIT ->
                RegisterPhoneScreen(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(top = innerPadding.calculateTopPadding()),
                    state = state,
                    onUsernameChanged = { onAction(RegisterAction.OnUsernameChanged(it)) },
                    onEmailChanged = { onAction(RegisterAction.OnEmailChanged(it)) },
                    onPasswordChanged = { onAction(RegisterAction.OnPasswordChanged(it)) },
                    onConfirmPasswordChanged = { onAction(RegisterAction.OnConfirmPasswordChanged(it)) },
                    onLoginClick = { onAction(RegisterAction.OnLoginClick) },
                    onRegisterClick = { onAction(RegisterAction.OnRegisterClick) }
                )

            DeviceLayoutType.LANDSCAPE ->
                RegisterPhoneLandscapeScreen(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(top = innerPadding.calculateTopPadding()),
                    state = state,
                    onUsernameChanged = { onAction(RegisterAction.OnUsernameChanged(it)) },
                    onEmailChanged = { onAction(RegisterAction.OnEmailChanged(it)) },
                    onPasswordChanged = { onAction(RegisterAction.OnPasswordChanged(it)) },
                    onConfirmPasswordChanged = { onAction(RegisterAction.OnConfirmPasswordChanged(it)) },
                    onLoginClick = { onAction(RegisterAction.OnLoginClick) },
                    onRegisterClick = { onAction(RegisterAction.OnRegisterClick) }
                )

            DeviceLayoutType.TABLET ->
                RegisterTabletScreen(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(top = innerPadding.calculateTopPadding()),
                    state = state,
                    onUsernameChanged = { onAction(RegisterAction.OnUsernameChanged(it)) },
                    onEmailChanged = { onAction(RegisterAction.OnEmailChanged(it)) },
                    onPasswordChanged = { onAction(RegisterAction.OnPasswordChanged(it)) },
                    onConfirmPasswordChanged = { onAction(RegisterAction.OnConfirmPasswordChanged(it)) },
                    onLoginClick = { onAction(RegisterAction.OnLoginClick) },
                    onRegisterClick = { onAction(RegisterAction.OnRegisterClick) }
                )
        }
    }
}