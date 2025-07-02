package com.jvrcoding.notemark.note.presentation.noteeditor

import androidx.compose.ui.text.input.TextFieldValue
import com.jvrcoding.notemark.core.domain.note.NoteId
import com.jvrcoding.notemark.note.presentation.noteeditor.componets.FabOption
import java.time.ZonedDateTime

data class NoteEditorState(
    val id: NoteId = "",
    val title: TextFieldValue = TextFieldValue(),
    val content: TextFieldValue = TextFieldValue(),
    val dateCreated: ZonedDateTime = ZonedDateTime.now(),
    val lastEdited: ZonedDateTime = ZonedDateTime.now(),
    val isSavingNote: Boolean = false,
    val showDiscardDialog: Boolean = false,
    val selectedFabOption: FabOption? = null,
    val isAdditionalUiVisible: Boolean = true
) {
    val screenMode: ScreenMode
        get() = when (selectedFabOption) {
            FabOption.Pen -> ScreenMode.EDIT
            FabOption.Book -> ScreenMode.READ
            null -> ScreenMode.VIEW
        }
}
