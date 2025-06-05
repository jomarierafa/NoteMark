package com.jvrcoding.notemark.core.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.jvrcoding.notemark.R

@Composable
fun NMHeader(
    modifier: Modifier = Modifier,
    headerText: String,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    headerTextStyle: TextStyle = MaterialTheme.typography.titleLarge
) {
    Column(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment
    ) {
        Text(
            text = headerText,
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