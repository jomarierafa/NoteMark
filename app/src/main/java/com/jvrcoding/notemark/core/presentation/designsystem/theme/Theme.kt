package com.jvrcoding.notemark.core.presentation.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val LightColorScheme = lightColorScheme(
    primary = BluePrimary,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    onPrimary = Color.White,
    surface = LightGray,
    onSurface = DarkGrayNeutral,
    onSurfaceVariant = SlateGray,
    error = AccentRed,
    surfaceContainerLowest = Color.White
)

@Composable
fun NoteMarkTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}