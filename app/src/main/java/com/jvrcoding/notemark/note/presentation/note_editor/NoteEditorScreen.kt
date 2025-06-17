package com.jvrcoding.notemark.note.presentation.note_editor

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.jvrcoding.notemark.R
import com.jvrcoding.notemark.core.presentation.components.NMToolbar
import com.jvrcoding.notemark.core.presentation.util.rememberDeviceLayoutType
import com.jvrcoding.notemark.note.presentation.notelist.NoteListAction
import com.jvrcoding.notemark.ui.theme.NoteMarkTheme

@Composable
fun NoteEditorScreenRoot(
    onBackClick: () -> Unit,
    onSaveNoteClick: () -> Unit
) {
    NoteEditorScreen(
        action = { action ->
            when (action) {
                NoteEditorAction.OnBackClick -> onBackClick()
                else -> Unit
            }
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditorScreen(
    action: (NoteEditorAction) -> Unit
) {

    val layoutType = rememberDeviceLayoutType()
    val toolbarPAdding = rememberToolbarPadding(layoutType)

    Scaffold(
        containerColor =  MaterialTheme.colorScheme.surface,
        topBar = {
            NMToolbar(
                modifier = Modifier.padding(toolbarPAdding),
                showBackButton = true,
                title = "",
                actionText = stringResource(R.string.save_note),
                onBackClick = { action(NoteEditorAction.OnBackClick) },
                onActionClick = { action(NoteEditorAction.OnSaveNoteClick) }
            )
        },
        modifier = Modifier.fillMaxSize()

    ) { innerPadding ->
        val layoutConfig = rememberNoteEditorLayoutConfig(layoutType, innerPadding)
        var firstText by remember { mutableStateOf("") }
        var secondText by remember { mutableStateOf("") }

        Box(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .zIndex(layoutConfig.boxZIndex)
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = layoutConfig.columnModifier
                    .fillMaxHeight(),
            ) {
                TextField(
                    value = firstText,
                    textStyle = layoutConfig.titleTextStyle,
                    onValueChange = { firstText = it },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.note_title),
                            style = layoutConfig.titleTextStyle,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    modifier = Modifier
                        .padding(horizontal = layoutConfig.textFieldPadding)
                        .fillMaxWidth(),
                    maxLines = 1,
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                    )

                )

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = secondText,
                    textStyle = MaterialTheme.typography.bodyLarge,
                    onValueChange = { secondText = it },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.tap_to_enter_note_content),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    modifier = Modifier
                        .padding(horizontal = layoutConfig.textFieldPadding)
                        .fillMaxWidth(),
                    maxLines = Int.MAX_VALUE,
                    singleLine = false,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun NoteEditorScreenPreview() {
    NoteMarkTheme {
        NoteEditorScreen(
            action = {}
        )
    }
}

@Preview(name = "Phone - Landscape",
    device = "spec:width = 411dp, height = 891dp, orientation = landscape, dpi = 420",
    showSystemUi = true)
@Composable
private fun NoteEditorScreenLandscapePreview() {
    NoteMarkTheme {
        NoteEditorScreen(
            action = {}
        )
    }
}


@Preview(
    name = "Tablet Portrait",
    showBackground = true,
    widthDp = 800,
    heightDp = 1280,
)
@Composable
private fun NoteEditorScreenTabletPreview() {
    NoteMarkTheme {
        NoteEditorScreen(
            action = {}
        )
    }
}