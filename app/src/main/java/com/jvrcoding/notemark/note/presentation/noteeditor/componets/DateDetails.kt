package com.jvrcoding.notemark.note.presentation.noteeditor.componets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jvrcoding.notemark.ui.theme.NoteMarkTheme

@Composable
fun DateDetails(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}


@Preview
@Composable
private fun DateDetailsPreview() {
    NoteMarkTheme {
        DateDetails(
            label = "Date Created",
            value = "26 Sep 2024, 18:54"
        )
    }
}