package com.jvrcoding.notemark.auth.presentation.login

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jvrcoding.notemark.auth.presentation.login.layout.LoginPhoneLandscapeScreen
import com.jvrcoding.notemark.auth.presentation.login.layout.LoginPhoneScreen
import com.jvrcoding.notemark.auth.presentation.login.layout.LoginTabletScreen
import com.jvrcoding.notemark.core.presentation.util.DeviceLayoutType
import com.jvrcoding.notemark.core.presentation.util.rememberDeviceLayoutType

@Composable
fun LoginScreenRoot(
    modifier: Modifier,
    onRegisterClick: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    LoginScreen(
        modifier = modifier,
        onAction = { action ->
            when(action) {
                LoginAction.OnLoginClick -> {

                }
                LoginAction.OnRegisterClick -> {
                    onRegisterClick()
                }
            }
        }
    )
}

@Composable
fun LoginScreen(
    modifier: Modifier,
    onAction: (LoginAction) -> Unit
) {
    val layoutType = rememberDeviceLayoutType()

    when (layoutType) {
        DeviceLayoutType.PORTRAIT ->
            LoginPhoneScreen(
                modifier = modifier,
                onLoginClick = { onAction(LoginAction.OnLoginClick) },
                onRegisterClick = { onAction(LoginAction.OnRegisterClick) }
            )
        DeviceLayoutType.LANDSCAPE ->
            LoginPhoneLandscapeScreen(
                modifier = modifier,
                onLoginClick = { onAction(LoginAction.OnLoginClick) },
                onRegisterClick = { onAction(LoginAction.OnRegisterClick) }
            )
        DeviceLayoutType.TABLET ->
            LoginTabletScreen(
                modifier = modifier,
                onLoginClick = { onAction(LoginAction.OnLoginClick) },
                onRegisterClick = { onAction(LoginAction.OnRegisterClick) }
            )
    }
}