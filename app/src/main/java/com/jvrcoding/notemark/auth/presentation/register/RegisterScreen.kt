package com.jvrcoding.notemark.auth.presentation.register

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jvrcoding.notemark.auth.presentation.register.layout.RegisterPhoneLandscapeScreen
import com.jvrcoding.notemark.auth.presentation.register.layout.RegisterPhoneScreen
import com.jvrcoding.notemark.core.presentation.util.DeviceLayoutType
import com.jvrcoding.notemark.core.presentation.util.rememberDeviceLayoutType

@Composable
fun RegisterScreenRoot(
    modifier: Modifier,
    onLoginClick: () -> Unit,
    onSuccessfulRegistration: () -> Unit,
) {
    RegisterScreen(
        modifier = modifier,
        onAction = { action ->
            when(action) {
                RegisterAction.OnLoginClick -> {
                    onLoginClick()
                }
                RegisterAction.OnRegisterClick -> {
                }
            }
        }
    )
}

@Composable
fun RegisterScreen(
    modifier: Modifier,
    onAction: (RegisterAction) -> Unit
) {
    val layoutType = rememberDeviceLayoutType()

    when (layoutType) {
        DeviceLayoutType.PORTRAIT ->
            RegisterPhoneScreen(
                modifier = modifier,
                onLoginClick = { onAction(RegisterAction.OnLoginClick) },
                onRegisterClick = { onAction(RegisterAction.OnRegisterClick) }
            )
        DeviceLayoutType.LANDSCAPE ->
            RegisterPhoneLandscapeScreen(
                modifier = modifier,
                onLoginClick = { onAction(RegisterAction.OnLoginClick) },
                onRegisterClick = { onAction(RegisterAction.OnRegisterClick) }
            )
        DeviceLayoutType.TABLET ->
            RegisterPhoneLandscapeScreen(
                modifier = modifier,
                onLoginClick = { onAction(RegisterAction.OnLoginClick) },
                onRegisterClick = { onAction(RegisterAction.OnRegisterClick) }
            )
    }
}