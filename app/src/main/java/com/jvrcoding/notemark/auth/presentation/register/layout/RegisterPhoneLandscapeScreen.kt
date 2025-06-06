package com.jvrcoding.notemark.auth.presentation.register.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jvrcoding.notemark.R
import com.jvrcoding.notemark.auth.presentation.register.components.RegisterFieldSection
import com.jvrcoding.notemark.core.presentation.NMHeader
import com.jvrcoding.notemark.ui.theme.NoteMarkTheme

@Composable
fun RegisterPhoneLandscapeScreen(
    modifier: Modifier = Modifier,
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .clip(
                RoundedCornerShape(
                    topStart = 20.dp,
                    topEnd = 20.dp
                )
            )
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            .padding(
                start = 60.dp,
                end = 40.dp
            )
    ) {
        NMHeader(
            headerText = stringResource(R.string.create_account),
            modifier = Modifier
                .weight(1f)
                .padding(top = 32.dp)
        )
        Spacer(modifier = Modifier.width(24.dp))
        RegisterFieldSection(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(vertical = 32.dp),
            onRegisterClick = { onRegisterClick() },
            onLoginClick = { onLoginClick() }
        )
    }
}

@Preview(name = "Phone - Landscape",
    device = "spec:width = 411dp, height = 891dp, orientation = landscape, dpi = 420",
    showSystemUi = true)
@Composable
private fun RegisterPhoneLandscapeScreenPreview() {
    NoteMarkTheme {
        Scaffold { innerPadding ->
            RegisterPhoneLandscapeScreen(
                modifier = Modifier
                    .padding(top = innerPadding.calculateTopPadding()),
                onRegisterClick = {},
                onLoginClick = {}
            )
        }
    }
}