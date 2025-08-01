package com.jvrcoding.notemark.note.presentation.noteeditor

import android.app.Activity
import android.content.pm.ActivityInfo
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jvrcoding.notemark.R
import com.jvrcoding.notemark.core.presentation.designsystem.components.NMToolbar
import com.jvrcoding.notemark.core.presentation.util.DeviceLayoutType
import com.jvrcoding.notemark.core.presentation.util.ObserveAsEvents
import com.jvrcoding.notemark.core.presentation.util.customTouch
import com.jvrcoding.notemark.core.presentation.util.formatWithJustNow
import com.jvrcoding.notemark.core.presentation.util.rememberDeviceLayoutType
import com.jvrcoding.notemark.note.presentation.noteeditor.componets.CustomFloatingActionButton
import com.jvrcoding.notemark.note.presentation.noteeditor.componets.DateDetails
import com.jvrcoding.notemark.core.presentation.designsystem.theme.ExtraSmallTitle
import com.jvrcoding.notemark.core.presentation.designsystem.theme.NoteMarkTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun NoteEditorScreenRoot(
    onSuccessfulDelete: () -> Unit,
    onSuccessfulSave: () -> Unit,
    onBackClick: () -> Unit,
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
            NoteEditorEvent.ExitScreen -> {
                val activity = context as? Activity
                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                onBackClick()
            }
        }
    }
    NoteEditorScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NoteEditorScreen(
    state: NoteEditorState,
    onAction: (NoteEditorAction) -> Unit
) {

    val density = LocalDensity.current
    val context = LocalContext.current

    val scrollState = rememberScrollState()
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()

    val layoutType = rememberDeviceLayoutType()
    val toolbarPAdding = rememberToolbarPadding(layoutType)

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(state.screenMode) {
        val activity = context as? Activity ?: return@LaunchedEffect
        when (state.screenMode) {
            ScreenMode.READ -> {
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }
            ScreenMode.EDIT -> {
                focusRequester.requestFocus()
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            }
            else -> {
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            }
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            AnimatedVisibility(
                visible = state.isNavBarVisible,
                enter = fadeIn(animationSpec = tween(durationMillis = 2000)),
                exit = fadeOut(animationSpec = tween(durationMillis = 2000))
            ) {
                NMToolbar(
                    showNavigationIcon = true,
                    showNoInternetIcon = false,
                    title = state.screenTitle,
                    titleStyle = ExtraSmallTitle,
                    modifier = Modifier
                        .background(
                            if (layoutType == DeviceLayoutType.LANDSCAPE)
                                Color.Transparent
                            else
                                MaterialTheme.colorScheme.surface
                        )
                        .padding(toolbarPAdding),
                    isEditMode = state.screenMode == ScreenMode.EDIT,
                    onNavIconClick = {
                        onAction(NoteEditorAction.OnNavIconClick)
                    },
                    actions = {
//                        if(state.screenMode == ScreenMode.EDIT) {
//                            Text(
//                                modifier = Modifier
//                                    .clickable { onAction(NoteEditorAction.OnSaveNoteClick) },
//                                text = stringResource(R.string.save_note),
//                                color = MaterialTheme.colorScheme.primary,
//                                style = ExtraSmallTitle
//                            )
//                        }
                    }
                )
            }
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = state.isAdditionalUiVisible,
                enter = fadeIn(animationSpec = tween(durationMillis = 2000)),
                exit = fadeOut(animationSpec = tween(durationMillis = 2000))
            ) {
                CustomFloatingActionButton(
                    selected = state.selectedFabOption,
                    onOptionSelected = { onAction(NoteEditorAction.OnSelectFabOption(it)) }
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        val layoutConfig = rememberNoteEditorLayoutConfig(layoutType, innerPadding)

        Box(
            modifier = Modifier
//                .zIndex(layoutConfig.boxZIndex)
                .customTouch(
                    onTap = {
                        onAction(NoteEditorAction.OnSurfaceTap)
                    },
                    onScroll = {
                        onAction(NoteEditorAction.OnStartScrolling)
                    }
                )
                .fillMaxSize()
                .imePadding()
                .verticalScroll(scrollState),
            contentAlignment = Alignment.TopCenter
        ) {
            // TODO(add row (X, Column, Save note))

            Column(
                modifier = layoutConfig.columnModifier
                    .wrapContentHeight(),
            ) {
                TextField(
                    readOnly = state.screenMode !=  ScreenMode.EDIT,
                    value = state.title,
                    textStyle = layoutConfig.titleTextStyle,
                    onValueChange = { onAction(NoteEditorAction.OnTitleChanged(it)) },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.note_title),
                            style = layoutConfig.titleTextStyle,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
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

                if(state.screenMode != ScreenMode.EDIT) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                horizontal = 16.dp,
                                vertical = 20.dp
                            )
                    ) {
                        DateDetails(
                            modifier = Modifier
                                .weight(1f),
                            label = stringResource(R.string.date_created),
                            value = state.dateCreated.formatWithJustNow()
                        )
                        DateDetails(
                            modifier = Modifier
                                .weight(1f),
                            label = stringResource(R.string.last_edited),
                            value = state.lastEdited.formatWithJustNow()
                        )
                    }
                    HorizontalDivider(thickness = 0.5.dp)
                }

                Spacer(modifier = Modifier.height(12.dp))

                BasicTextField(
                    readOnly = state.screenMode !=  ScreenMode.EDIT,
                    value = state.content,
                    onValueChange = { onAction(NoteEditorAction.OnContentChanged(it)) },
                    modifier = Modifier
                        .padding(horizontal = layoutConfig.textFieldPadding)
                        .fillMaxWidth()
                        .bringIntoViewRequester(bringIntoViewRequester),
                    onTextLayout = { textLayoutResult ->
                        val cursorRect = textLayoutResult.getCursorRect(state.content.selection.start)

                        coroutineScope.launch {
                            bringIntoViewRequester.bringIntoView(
                                cursorRect.copy(
                                    // scroll a bit extra
                                    bottom = cursorRect.bottom + with(density) { 80.dp.toPx() }
                                )
                            )
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
            state = NoteEditorState(),
            onAction = {},
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
            state = NoteEditorState(),
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
private fun NoteEditorScreenTabletPreview() {
    NoteMarkTheme {
        NoteEditorScreen(
            state = NoteEditorState(),
            onAction = {}
        )
    }
}