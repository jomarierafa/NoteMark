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
import androidx.compose.ui.tooling.preview.Preview
import com.jvrcoding.notemark.R
import com.jvrcoding.notemark.ui.theme.ChevronBackIcon
import com.jvrcoding.notemark.ui.theme.NoteMarkTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NMToolbar(
    modifier: Modifier = Modifier,
    title: String = "",
    titleColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    onBackClick: () -> Unit = {},
    scrollBehaviour: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    navigationIcon: @Composable () -> Unit = {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = ChevronBackIcon,
                contentDescription = stringResource(R.string.go_back),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    },
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = titleColor,
                style = MaterialTheme.typography.titleMedium
            )
        },
        modifier = modifier,
        scrollBehavior = scrollBehaviour,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        navigationIcon = navigationIcon,
        actions = actions
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun NMToolbarPreview() {
    NoteMarkTheme {
        NMToolbar(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
        )
    }
}