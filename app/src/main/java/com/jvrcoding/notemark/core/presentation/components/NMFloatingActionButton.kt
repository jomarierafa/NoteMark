package com.jvrcoding.notemark.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jvrcoding.notemark.R
import com.jvrcoding.notemark.ui.theme.AddIcon
import com.jvrcoding.notemark.ui.theme.NoteMarkTheme

@Composable
fun NMFloatingActionButton(
    modifier: Modifier = Modifier,
    onFabClick: () -> Unit
) {
    IconButton(
        onClick = { onFabClick() },
        modifier = modifier
            .shadow(
                elevation = 24.dp,
                shape = RoundedCornerShape(14.dp),
                ambientColor = Color(0x1F1B1B1C),
                spotColor = Color(0x1F1B1B1C)
            )
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF58A1F8), Color(0xFF5A4CF7))
                ),
                shape = RoundedCornerShape(14.dp)
            )
            .size(64.dp)

    ) {
        Icon(
            imageVector = AddIcon,
            contentDescription = stringResource(R.string.add_button),
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview
@Composable
private fun NMFloatingActionButtonPreview() {
    NoteMarkTheme {
        NMFloatingActionButton(
            onFabClick = {}
        )
    }
}