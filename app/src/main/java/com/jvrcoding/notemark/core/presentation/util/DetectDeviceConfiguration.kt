package com.jvrcoding.notemark.core.presentation.util

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import kotlin.math.min

enum class DeviceLayoutType {
    PORTRAIT, LANDSCAPE, TABLET
}

@Composable
fun rememberDeviceLayoutType(): DeviceLayoutType {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val screenHeightDp = configuration.screenHeightDp

    return when {
        min(screenWidthDp, screenHeightDp) >= 600 -> DeviceLayoutType.TABLET
        configuration.orientation == Configuration.ORIENTATION_LANDSCAPE -> DeviceLayoutType.LANDSCAPE
        else -> DeviceLayoutType.PORTRAIT
    }
}