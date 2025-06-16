package com.jvrcoding.notemark.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jvrcoding.notemark.R
import com.jvrcoding.notemark.ui.theme.CrossIcon
import com.jvrcoding.notemark.ui.theme.ExtraSmallTitle
import com.jvrcoding.notemark.ui.theme.NoteMarkTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NMToolbar(
    showBackButton: Boolean,
    title: String,
    modifier: Modifier = Modifier,
    actionText: String? = null,
    nameInitial: String? = null,
    onBackClick: () -> Unit = {},
    onActionClick: () -> Unit = {},
    scrollBehaviour: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleMedium
            )
        },
        modifier = modifier,
        scrollBehavior = scrollBehaviour,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        navigationIcon = {
            if(showBackButton) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = CrossIcon,
                        contentDescription = stringResource(R.string.go_back),
                    )
                }
            }
        },
        actions = {
            actionText?.let {
                Text(
                    modifier = Modifier
                        .clickable { onActionClick() },
                    text = it,
                    color = MaterialTheme.colorScheme.primary,
                    style = ExtraSmallTitle
                )
            }

            nameInitial?.let {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun NMToolbarPreview() {
    NoteMarkTheme {
        NMToolbar(
            showBackButton = false,
            title = "NoteMark",
            nameInitial = "JR",
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
        )
    }
}