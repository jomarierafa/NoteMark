package com.jvrcoding.notemark.auth.presentation.login.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jvrcoding.notemark.R
import com.jvrcoding.notemark.auth.presentation.login.LoginState
import com.jvrcoding.notemark.auth.presentation.login.components.LoginFieldSection
import com.jvrcoding.notemark.core.presentation.components.NMHeader
import com.jvrcoding.notemark.core.presentation.designsystem.theme.NoteMarkTheme

@Composable
fun LoginPhoneLandscapeScreen(
    modifier: Modifier = Modifier,
    state: LoginState,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
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
                top = 32.dp,
                start = 60.dp,
                end = 40.dp
            )
    ) {
        NMHeader(
            headerText = stringResource(R.string.log_in),
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(24.dp))
        LoginFieldSection(
            modifier = Modifier
                .weight(1f),
            state = state,
            onEmailChanged = { onEmailChanged(it) },
            onPasswordChanged = { onPasswordChanged(it) },
            onRegisterClick = { onRegisterClick() },
            onLoginClick = { onLoginClick() }
        )
    }
}

@Preview(name = "Phone - Landscape",
    device = "spec:width = 411dp, height = 891dp, orientation = landscape, dpi = 420",
    showSystemUi = true)
@Composable
private fun LoginPhoneLandscapeScreenPreview() {
    NoteMarkTheme {
        Scaffold { innerPadding ->
            LoginPhoneLandscapeScreen(
                modifier = Modifier
                    .padding(top = innerPadding.calculateTopPadding()),
                state = LoginState(),
                onEmailChanged = {},
                onPasswordChanged = {},
                onLoginClick = {},
                onRegisterClick = {}
            )
        }
    }
}