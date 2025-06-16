package com.jvrcoding.notemark.auth.presentation.landing

import androidx.compose.runtime.Composable
import com.jvrcoding.notemark.auth.presentation.landing.layout.LandingPhoneLandscapeScreen
import com.jvrcoding.notemark.auth.presentation.landing.layout.LandingPhoneScreen
import com.jvrcoding.notemark.auth.presentation.landing.layout.LandingTabletScreen
import com.jvrcoding.notemark.core.presentation.util.DeviceLayoutType
import com.jvrcoding.notemark.core.presentation.util.rememberDeviceLayoutType



@Composable
fun LandingScreenRoot(
    onGetStartedClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    LandingScreen(
        onAction = { action ->
            when(action) {
                LandingAction.OnGetStartedClick -> { onGetStartedClick() }
                LandingAction.OnLoginClick -> { onLoginClick() }
            }
        }
    )
}



@Composable
fun LandingScreen(
    onAction: (LandingAction) -> Unit
) {
    val layoutType = rememberDeviceLayoutType()

    when (layoutType) {
        DeviceLayoutType.PORTRAIT ->
            LandingPhoneScreen(
                onGetStartedClick = { onAction(LandingAction.OnGetStartedClick) },
                onLoginClick = { onAction(LandingAction.OnLoginClick) }
            )
        DeviceLayoutType.LANDSCAPE ->
            LandingPhoneLandscapeScreen(
                onGetStartedClick = { onAction(LandingAction.OnGetStartedClick) },
                onLoginClick = { onAction(LandingAction.OnLoginClick) }
            )
        DeviceLayoutType.TABLET ->
            LandingTabletScreen(
                onGetStartedClick = { onAction(LandingAction.OnGetStartedClick) },
                onLoginClick = { onAction(LandingAction.OnLoginClick) }
            )
    }
}