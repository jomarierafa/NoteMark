package com.jvrcoding.notemark.auth.presentation.login.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.jvrcoding.notemark.R
import com.jvrcoding.notemark.ui.theme.NoteMarkTheme

@Composable
fun LoginHeader(
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    headerTextStyle: TextStyle = MaterialTheme.typography.titleLarge,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment
    ) {
        Text(
            text = stringResource(R.string.log_in),
            style = headerTextStyle,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = stringResource(R.string.capture_your_thoughts_and_ideas),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview
@Composable
private fun LoginHeaderPreview() {
    NoteMarkTheme {
        LoginHeader(
            modifier = Modifier.background(Color.White)
        )
    }
}