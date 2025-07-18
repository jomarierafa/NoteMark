package com.jvrcoding.notemark.core.presentation.designsystem.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.jvrcoding.notemark.core.presentation.designsystem.theme.NoteMarkTheme

@Composable
fun NMCommonDialog(
    title: String,
    text: String,
    positiveButtonText: String = "Yes",
    negativeButtonText: String = "No",
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
        AlertDialog(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
            onDismissRequest = onDismiss,
            title = {
                Text(text = title)
            },
            text = {
                Text(text = text)
            },
            confirmButton = {
                TextButton(onClick = onConfirm) {
                    Text(text  = positiveButtonText)
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(text = negativeButtonText)
                }
            }
        )
}

@Preview
@Composable
private fun NMCommonDialogPreview() {
    NoteMarkTheme {
        NMCommonDialog(
            title = "Confirm Action",
            text = "Are you sure you want to proceed?",
            onDismiss = {},
            onConfirm = {}
        )
    }
}