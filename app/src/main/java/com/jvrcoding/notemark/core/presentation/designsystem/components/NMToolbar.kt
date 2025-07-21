package com.jvrcoding.notemark.core.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jvrcoding.notemark.R
import com.jvrcoding.notemark.core.presentation.designsystem.theme.ChevronBackIcon
import com.jvrcoding.notemark.core.presentation.designsystem.theme.CloudOffIcon
import com.jvrcoding.notemark.core.presentation.designsystem.theme.CrossIcon
import com.jvrcoding.notemark.core.presentation.designsystem.theme.NoteMarkTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NMToolbar(
    showNavigationIcon: Boolean,
    showNoInternetIcon: Boolean,
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = title,
                    color = titleColor,
                    style = titleStyle
                )
                if(showNoInternetIcon) {
                    Icon(
                        imageVector = CloudOffIcon,
                        contentDescription = stringResource(R.string.no_internet_icon),
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                    )
                }
            }
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
            title = "NoteMark",
            showNavigationIcon = true,
            showNoInternetIcon = true,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
        )
    }
}