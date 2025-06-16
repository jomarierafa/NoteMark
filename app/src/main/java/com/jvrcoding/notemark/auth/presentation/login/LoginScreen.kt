package com.jvrcoding.notemark.auth.presentation.login

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
import com.jvrcoding.notemark.auth.presentation.login.layout.LoginPhoneLandscapeScreen
import com.jvrcoding.notemark.auth.presentation.login.layout.LoginPhoneScreen
import com.jvrcoding.notemark.auth.presentation.login.layout.LoginTabletScreen
import com.jvrcoding.notemark.core.presentation.util.DeviceLayoutType
import com.jvrcoding.notemark.core.presentation.util.ObserveAsEvents
import com.jvrcoding.notemark.core.presentation.util.rememberDeviceLayoutType
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreenRoot(
    onRegisterClick: () -> Unit,
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    ObserveAsEvents(viewModel.events) { event ->
        when(event) {
            is LoginEvent.Error -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    event.error.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
            LoginEvent.LoginSuccess -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    R.string.youre_logged_in,
                    Toast.LENGTH_LONG
                ).show()

                onLoginSuccess()
            }
        }
    }

    LoginScreen(
        state = viewModel.state,
        onAction = { action ->
            when(action) {
                is LoginAction.OnRegisterClick -> onRegisterClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit
) {

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        val layoutType = rememberDeviceLayoutType()

        when (layoutType) {
            DeviceLayoutType.PORTRAIT ->
                LoginPhoneScreen(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(top = innerPadding.calculateTopPadding()),
                    state = state,
                    onEmailChanged = { onAction(LoginAction.OnEmailChanged(it)) },
                    onPasswordChanged = { onAction(LoginAction.OnPasswordChanged(it)) },
                    onLoginClick = { onAction(LoginAction.OnLoginClick) },
                    onRegisterClick = { onAction(LoginAction.OnRegisterClick) }
                )
            DeviceLayoutType.LANDSCAPE ->
                LoginPhoneLandscapeScreen(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(top = innerPadding.calculateTopPadding()),
                    state = state,
                    onEmailChanged = { onAction(LoginAction.OnEmailChanged(it)) },
                    onPasswordChanged = { onAction(LoginAction.OnPasswordChanged(it)) },
                    onLoginClick = { onAction(LoginAction.OnLoginClick) },
                    onRegisterClick = { onAction(LoginAction.OnRegisterClick) }
                )
            DeviceLayoutType.TABLET ->
                LoginTabletScreen(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(top = innerPadding.calculateTopPadding()),
                    state = state,
                    onEmailChanged = { onAction(LoginAction.OnEmailChanged(it)) },
                    onPasswordChanged = { onAction(LoginAction.OnPasswordChanged(it)) },
                    onLoginClick = { onAction(LoginAction.OnLoginClick) },
                    onRegisterClick = { onAction(LoginAction.OnRegisterClick) }
                )
        }
    }

}