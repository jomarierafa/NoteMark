package com.jvrcoding.notemark.note.presentation.notelist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jvrcoding.notemark.core.presentation.components.NMFloatingActionButton
import com.jvrcoding.notemark.core.presentation.components.NMToolbar
import com.jvrcoding.notemark.core.presentation.util.rememberDeviceLayoutType
import com.jvrcoding.notemark.note.presentation.notelist.components.NoteListItem
import com.jvrcoding.notemark.ui.theme.NoteMarkTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun NoteListScreenRoot(
    onAddClick: () -> Unit,
    viewModel: NoteListViewModel = koinViewModel()
) {
    NoteListScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                NoteListAction.OnAddButtonClick -> onAddClick()
                else -> Unit
            }
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
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surfaceContainerLowest)
                    .padding(layoutConfig.toolBarPadding),
                showBackButton = false,
                title = "NoteMark",
                nameInitial = "JR",
            )
        },
        floatingActionButton = {
            NMFloatingActionButton(onFabClick = {
                onAction(NoteListAction.OnAddButtonClick)
            })
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
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
            items(state.notes) { notes ->
                NoteListItem(
                    noteUi = notes,
                    labelMaxChar = layoutConfig.maxLabelChar,
                    titleTextStyle = layoutConfig.titleTextStyle,
                    labelTextStyle = layoutConfig.labelTextStyle
                )
            }
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
            state = NoteListState(),
            onAction = {}
        )
    }
}