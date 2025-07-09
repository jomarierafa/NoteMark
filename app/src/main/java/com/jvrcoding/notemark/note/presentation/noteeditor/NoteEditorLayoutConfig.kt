package com.jvrcoding.notemark.note.presentation.noteeditor

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jvrcoding.notemark.core.presentation.util.DeviceLayoutType
import com.jvrcoding.notemark.ui.theme.ExtraLargeTitle

data class NoteEditorLayoutConfig(
    val boxZIndex: Float,
    val columnModifier: Modifier,
    val titleTextStyle: TextStyle,
    val textFieldPadding: Dp
)

@Composable
fun rememberToolbarPadding(layoutType: DeviceLayoutType): PaddingValues {
    return when (layoutType) {
        DeviceLayoutType.PORTRAIT ->  PaddingValues(end = 16.dp)
        DeviceLayoutType.LANDSCAPE -> PaddingValues(start = 35.dp, end = 8.dp)
        DeviceLayoutType.TABLET -> PaddingValues(start = 16.dp, end = 24.dp)
    }
}

@Composable
fun rememberNoteEditorLayoutConfig(
    layoutType: DeviceLayoutType,
    paddingValues: PaddingValues
): NoteEditorLayoutConfig {
    return when (layoutType) {
        DeviceLayoutType.PORTRAIT -> NoteEditorLayoutConfig(
            boxZIndex = 0f,
            columnModifier = Modifier
                .fillMaxWidth()
                .padding(top = paddingValues.calculateTopPadding()),
            titleTextStyle = MaterialTheme.typography.titleLarge,
            textFieldPadding = 0.dp

        )
        DeviceLayoutType.LANDSCAPE -> NoteEditorLayoutConfig(
            boxZIndex = 1f,
            columnModifier = Modifier
                .fillMaxWidth(0.57f)
                .padding(top = WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding())
            ,
            titleTextStyle = MaterialTheme.typography.titleLarge,
            textFieldPadding = 0.dp
        )
        DeviceLayoutType.TABLET -> NoteEditorLayoutConfig(
            boxZIndex = 0f,
            columnModifier = Modifier
                .fillMaxWidth()
                .padding(top = paddingValues.calculateTopPadding()),
            titleTextStyle = ExtraLargeTitle,
            textFieldPadding = 16.dp
        )
    }
}