package com.jvrcoding.notemark.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.jvrcoding.notemark.R
import com.jvrcoding.notemark.core.presentation.designsystem.theme.ChevronBackIcon
import com.jvrcoding.notemark.core.presentation.designsystem.theme.CrossIcon
import com.jvrcoding.notemark.core.presentation.designsystem.theme.NoteMarkTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NMToolbar(
    showNavigationIcon: Boolean,
    modifier: Modifier = Modifier,
    title: String = "",
    titleColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    titleStyle: TextStyle = MaterialTheme.typography.titleMedium,
    onNavIconClick: () -> Unit = {},
    scrollBehaviour: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    isEditMode: Boolean = false,
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = titleColor,
                style = titleStyle
            )
        },
        modifier = modifier,
        scrollBehavior = scrollBehaviour,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        navigationIcon = {
            if(!showNavigationIcon) return@TopAppBar

            IconButton(onClick = onNavIconClick) {
                if(isEditMode) {
                    Icon(
                        imageVector = CrossIcon,
                        contentDescription = stringResource(R.string.go_back),
                    )
                } else {
                    Icon(
                        imageVector = ChevronBackIcon,
                        contentDescription = stringResource(R.string.go_back),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        },
        actions = actions
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun NMToolbarPreview() {
    NoteMarkTheme {
        NMToolbar(
            showNavigationIcon = true,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
        )
    }
}