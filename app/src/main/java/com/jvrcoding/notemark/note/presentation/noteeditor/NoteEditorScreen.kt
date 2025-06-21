package com.jvrcoding.notemark.note.presentation.noteeditor

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jvrcoding.notemark.R
import com.jvrcoding.notemark.core.domain.note.NoteId
import com.jvrcoding.notemark.core.presentation.components.NMToolbar
import com.jvrcoding.notemark.core.presentation.util.ObserveAsEvents
import com.jvrcoding.notemark.core.presentation.util.rememberDeviceLayoutType
import com.jvrcoding.notemark.ui.theme.NoteMarkTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun NoteEditorScreenRoot(
    id: NoteId,
    onSuccessfulDelete: () -> Unit,
    onSuccessfulSave: () -> Unit,
    viewModel: NoteEditorViewModel = koinViewModel()
) {

    val context = LocalContext.current
    ObserveAsEvents(flow = viewModel.events) { event ->
        when(event) {
            is NoteEditorEvent.Error -> {
                Toast.makeText(
                    context,
                    event.error.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
            NoteEditorEvent.NoteSaved -> onSuccessfulSave()
            NoteEditorEvent.NoteDeleted -> onSuccessfulDelete()
        }
    }
    NoteEditorScreen(
        id = id,
        state = viewModel.state,
        action = viewModel::onAction
//            { action ->
//            when (action) {
//                NoteEditorAction.OnBackClick -> onBackClick()
//                else -> Unit
//            }
//            viewModel.onAction(action)
//        }
    )

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NoteEditorScreen(
    id: NoteId,
    state: NoteEditorState,
    action: (NoteEditorAction) -> Unit
) {

    val scrollState = rememberScrollState()
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()


    val layoutType = rememberDeviceLayoutType()
    val toolbarPAdding = rememberToolbarPadding(layoutType)

    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        action(NoteEditorAction.GetNote(id))
        focusRequester.requestFocus()
    }



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

        Box(
            modifier = Modifier
                .verticalScroll(scrollState)
//                .zIndex(layoutConfig.boxZIndex)
                .fillMaxSize()
                .imePadding(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = layoutConfig.columnModifier
                    .fillMaxHeight(),
            ) {
                TextField(
                    value = state.title,
                    textStyle = layoutConfig.titleTextStyle,
                    onValueChange = { action(NoteEditorAction.OnTitleChanged(it)) },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.note_title),
                            style = layoutConfig.titleTextStyle,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    modifier = Modifier
                        .padding(horizontal = layoutConfig.textFieldPadding)
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    maxLines = 1,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
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

                BasicTextField(
                    value = state.content,
                    onValueChange = { action(NoteEditorAction.OnContentChanged(it)) },
                    modifier = Modifier
                        .padding(horizontal = layoutConfig.textFieldPadding)
                        .fillMaxWidth()
                        .bringIntoViewRequester(bringIntoViewRequester),
                    onTextLayout = { textLayoutResult ->
                        val cursorRect = textLayoutResult.getCursorRect(state.content.selection.start)

                        coroutineScope.launch {
                            bringIntoViewRequester.bringIntoView(cursorRect)
                        }
                    },
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        color =  MaterialTheme.colorScheme.onSurfaceVariant,
                        textDecoration = TextDecoration.None
                    ),
                    maxLines = Int.MAX_VALUE,
                    singleLine = false,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.surface)
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                                .fillMaxWidth()
                        ) {
                            if (state.content.text.isEmpty()) {
                                Text(
                                    text = stringResource(R.string.tap_to_enter_note_content),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                            innerTextField()
                        }
                    }
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
            id = "",
            state = NoteEditorState(),
            action = {},
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
            id = "",
            state = NoteEditorState(),
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
            id = "",
            state = NoteEditorState(),
            action = {}
        )
    }
}