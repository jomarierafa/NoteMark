package com.jvrcoding.notemark.core.presentation.designsystem.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val BluePrimary = Color(0xFF5977F7)
val DarkGrayNeutral = Color(0xFF1B1B1C)
val SlateGray = Color(0xFF535364)
val LightGray = Color(0xFFEFEFF2)
val AccentRed = Color(0xFFE1294B)
val SoftBlue = Color(0xFFE0EAFF)
val Shadow = Color(0x141B1B1C)

val ColorScheme.fabGradient: Brush
    get() = Brush.linearGradient(
        listOf(
            Color(0xFF58A1F8),
            Color(0xFF5A4CF7)
        )
    )