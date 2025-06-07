package com.jvrcoding.notemark.auth.presentation.login

import android.widget.Toast
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
    modifier: Modifier,
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
        modifier = modifier,
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
    modifier: Modifier,
    state: LoginState,
    onAction: (LoginAction) -> Unit
) {
    val layoutType = rememberDeviceLayoutType()

    when (layoutType) {
        DeviceLayoutType.PORTRAIT ->
            LoginPhoneScreen(
                modifier = modifier,
                state = state,
                onEmailChanged = { onAction(LoginAction.OnEmailChanged(it)) },
                onPasswordChanged = { onAction(LoginAction.OnPasswordChanged(it)) },
                onLoginClick = { onAction(LoginAction.OnLoginClick) },
                onRegisterClick = { onAction(LoginAction.OnRegisterClick) }
            )
        DeviceLayoutType.LANDSCAPE ->
            LoginPhoneLandscapeScreen(
                modifier = modifier,
                state = state,
                onEmailChanged = { onAction(LoginAction.OnEmailChanged(it)) },
                onPasswordChanged = { onAction(LoginAction.OnPasswordChanged(it)) },
                onLoginClick = { onAction(LoginAction.OnLoginClick) },
                onRegisterClick = { onAction(LoginAction.OnRegisterClick) }
            )
        DeviceLayoutType.TABLET ->
            LoginTabletScreen(
                modifier = modifier,
                state = state,
                onEmailChanged = { onAction(LoginAction.OnEmailChanged(it)) },
                onPasswordChanged = { onAction(LoginAction.OnPasswordChanged(it)) },
                onLoginClick = { onAction(LoginAction.OnLoginClick) },
                onRegisterClick = { onAction(LoginAction.OnRegisterClick) }
            )
    }
}