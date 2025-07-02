package com.jvrcoding.notemark.note.presentation.noteeditor.componets

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jvrcoding.notemark.R
import com.jvrcoding.notemark.ui.theme.NoteMarkTheme

@Composable
fun CustomFloatingActionButton(
    selected: FabOption? = null,
    onOptionSelected: (FabOption) -> Unit
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(4.dp)
    ) {
        FabOption.entries.forEach { option ->
            val isSelected = selected == option
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onOptionSelected(option) }
                    .background(
                        if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                        else Color.Transparent
                    )
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier
                        .size(20.dp),
                    painter = painterResource(id = option.iconRes),
                    contentDescription = null,
                    tint = if (isSelected)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

enum class FabOption(@DrawableRes val iconRes: Int) {
    Pen(R.drawable.ic_pen),
    Book(R.drawable.ic_book)
}


@Preview
@Composable
private fun CustomFloatingActionButtonPreview() {
    NoteMarkTheme {
        CustomFloatingActionButton(
            selected = FabOption.Pen,
            onOptionSelected = {}
        )
    }
}