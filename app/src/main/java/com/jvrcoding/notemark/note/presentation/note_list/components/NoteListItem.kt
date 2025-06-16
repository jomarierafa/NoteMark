package com.jvrcoding.notemark.note.presentation.note_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jvrcoding.notemark.core.presentation.util.previewWithEllipsis
import com.jvrcoding.notemark.ui.theme.NoteMarkTheme
import com.jvrcoding.notemark.ui.theme.Shadow

@Composable
fun NoteListItem(
    modifier: Modifier = Modifier,
    date: String,
    title: String,
    label: String,
    titleTextStyle: TextStyle = MaterialTheme.typography.titleMedium,
    labelTextStyle: TextStyle= MaterialTheme.typography.bodySmall,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = date,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSurface,
            style = titleTextStyle
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = labelTextStyle,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
private fun NoteListItemPreview() {
    NoteMarkTheme {
        NoteListItem(
            modifier = Modifier
                .padding(16.dp)
                .shadow(
                    elevation = 24.dp,
                    shape = RoundedCornerShape(12.dp),
                    ambientColor = Shadow ,
                    spotColor = Shadow
                )
                .background(
                    MaterialTheme.colorScheme.surfaceContainerLowest,
                    RoundedCornerShape(12.dp)
                )
                .padding(16.dp),
            date = "19 APR",
            title = "Title of the note",
            label = "Augue non mauris ante viverra ut arcu sed ut lectus interdum morbi sed leo purus gravida non id mi augue.".previewWithEllipsis(50)
        )

    }
}