package com.jvrcoding.notemark.note.presentation.notelist

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.jvrcoding.notemark.core.presentation.util.DeviceLayoutType

data class NoteListLayoutConfig(
    val columns: Int,
    val titleTextStyle: TextStyle,
    val labelTextStyle: TextStyle,
    val contentPadding: PaddingValues,
    val maxLabelChar: Int,
    val toolBarPadding: PaddingValues
)

@Composable
fun rememberNoteListLayoutConfig(layoutType: DeviceLayoutType): NoteListLayoutConfig {
    return when (layoutType) {
        DeviceLayoutType.PORTRAIT -> NoteListLayoutConfig(
            columns = 2,
            titleTextStyle = MaterialTheme.typography.titleMedium,
            labelTextStyle = MaterialTheme.typography.bodySmall,
            contentPadding = PaddingValues(16.dp),
            maxLabelChar = 50,
            toolBarPadding = PaddingValues(end = 16.dp)
        )
        DeviceLayoutType.LANDSCAPE -> NoteListLayoutConfig(
            columns = 3,
            titleTextStyle = MaterialTheme.typography.titleMedium,
            labelTextStyle = MaterialTheme.typography.bodySmall,
            contentPadding = PaddingValues(start = 60.dp, top = 16.dp, end = 16.dp),
            maxLabelChar = 50,
            toolBarPadding = PaddingValues(start = 45.dp, end = 8.dp)
        )
        DeviceLayoutType.TABLET -> NoteListLayoutConfig(
            columns = 2,
            titleTextStyle = MaterialTheme.typography.titleLarge,
            labelTextStyle = MaterialTheme.typography.bodyLarge,
            contentPadding = PaddingValues(vertical = 16.dp, horizontal = 24.dp),
            maxLabelChar = 250,
            toolBarPadding = PaddingValues(start = 16.dp, end = 24.dp)
        )
    }
}