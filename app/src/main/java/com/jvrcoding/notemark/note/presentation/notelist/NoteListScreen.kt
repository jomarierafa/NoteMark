package com.jvrcoding.notemark.note.presentation.notelist

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jvrcoding.notemark.R
import com.jvrcoding.notemark.core.domain.note.NoteId
import com.jvrcoding.notemark.core.presentation.components.NMCommonDialog
import com.jvrcoding.notemark.core.presentation.components.NMFloatingActionButton
import com.jvrcoding.notemark.core.presentation.components.NMToolbar
import com.jvrcoding.notemark.core.presentation.util.ObserveAsEvents
import com.jvrcoding.notemark.core.presentation.util.getInitials
import com.jvrcoding.notemark.core.presentation.util.rememberDeviceLayoutType
import com.jvrcoding.notemark.note.presentation.notelist.components.NoteListItem
import com.jvrcoding.notemark.core.presentation.designsystem.theme.NoteMarkTheme
import com.jvrcoding.notemark.core.presentation.designsystem.theme.SettingIcon
import org.koin.androidx.compose.koinViewModel

@Composable
fun NoteListScreenRoot(
    onSettingsClick: () -> Unit,
    onSuccessfulAdd: (NoteId) -> Unit,
    onTapNote: (NoteId) -> Unit,
    viewModel: NoteListViewModel = koinViewModel()
) {

    val context = LocalContext.current
    ObserveAsEvents(flow = viewModel.events) { event ->
        when(event) {
            is NoteListEvent.Error -> {
                Toast.makeText(
                    context,
                    event.error.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
            is NoteListEvent.NoteSaved -> onSuccessfulAdd(event.noteId)
        }
    }
    NoteListScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                NoteListAction.OnSettingsClick -> onSettingsClick()
                is NoteListAction.OnTapNote -> onTapNote(action.id)
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(
    state: NoteListState,
    onAction: (NoteListAction) -> Unit
) {
    val layoutType = rememberDeviceLayoutType()
    val layoutConfig = rememberNoteListLayoutConfig(layoutType)


    Scaffold(
        topBar = {
            NMToolbar(
                showNavigationIcon = false,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surfaceContainerLowest)
                    .padding(layoutConfig.toolBarPadding),
                title = stringResource(R.string.notemark),
                titleColor = MaterialTheme.colorScheme.onSurface,
                actions = {
                    IconButton(onClick = { onAction(NoteListAction.OnSettingsClick) }) {
                        Icon(
                            imageVector = SettingIcon,
                            contentDescription = stringResource(R.string.go_back),
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = state.username.getInitials(),
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            NMFloatingActionButton(
                isLoading = state.isAddingNote,
                onFabClick = {
                    onAction(NoteListAction.OnAddButtonClick)
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        if (state.notes.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(top = innerPadding.calculateTopPadding())
                    .background(MaterialTheme.colorScheme.surface)
                    .fillMaxSize()
            ) {
                Text(
                    text = stringResource(R.string.empty_note_message),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 80.dp)
                )
            }
        } else {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(layoutConfig.columns),
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(top = innerPadding.calculateTopPadding()),
                contentPadding = layoutConfig.contentPadding,
                verticalItemSpacing = 16.dp,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(
                    items = state.notes,
                    key = {  it.id }
                ) { note ->
                    NoteListItem(
                        modifier = Modifier
                            .animateItem()
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onTap = {
                                        onAction(NoteListAction.OnTapNote(note.id))
                                    },
                                    onLongPress = {
                                        onAction(NoteListAction.OnNoteLongPressed(note.id))
                                    }
                                )
                            },
                        noteUi = note,
                        labelMaxChar = layoutConfig.maxLabelChar,
                        titleTextStyle = layoutConfig.titleTextStyle,
                        labelTextStyle = layoutConfig.labelTextStyle
                    )
                }
            }
        }

        state.noteToDelete?.let { noteId ->
            NMCommonDialog(
                title = stringResource(R.string.delete_note),
                text = stringResource(
                    R.string.are_you_sure_you_want_to_delete_this_note_this_action_cannot_be_undone
                ),
                positiveButtonText = stringResource(R.string.delete),
                negativeButtonText = stringResource(R.string.cancel),
                onDismiss = {
                    onAction(NoteListAction.DismissDeleteDialog)
                },
                onConfirm = {
                    onAction(NoteListAction.DeleteNote(noteId))
                }
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun NoteListScreenPreview() {
    NoteMarkTheme {
        NoteListScreen(
            state = NoteListState(),
            onAction = {}
        )
    }
}

@Preview(name = "Phone - Landscape",
    device = "spec:width = 411dp, height = 891dp, orientation = landscape, dpi = 420",
    showSystemUi = true)
@Composable
private fun NoteListScreenLandscapePreview() {
    NoteMarkTheme {
        NoteListScreen(
            state = NoteListState(),
            onAction = {}
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
private fun NoteListScreenTabletPreview() {
    NoteMarkTheme {
        NoteListScreen(
            state = NoteListState(
                notes = listOf()
            ),
            onAction = {}
        )
    }
}