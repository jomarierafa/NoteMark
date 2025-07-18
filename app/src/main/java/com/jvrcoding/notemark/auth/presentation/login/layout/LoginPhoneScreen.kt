package com.jvrcoding.notemark.auth.presentation.login.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.jvrcoding.notemark.core.presentation.designsystem.components.NMHeader
import com.jvrcoding.notemark.core.presentation.designsystem.theme.NoteMarkTheme

@Composable
fun LoginPhoneScreen(
    modifier: Modifier = Modifier,
    state: LoginState,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    Column(
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
                horizontal = 16.dp,
                vertical = 32.dp
            )
    ) {
        NMHeader(headerText = stringResource(R.string.log_in))
        Spacer(modifier = Modifier.height(24.dp))
        LoginFieldSection(
            state = state,
            onEmailChanged = { onEmailChanged(it) },
            onPasswordChanged = { onPasswordChanged(it) },
            onRegisterClick = { onRegisterClick() },
            onLoginClick = { onLoginClick() }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LoginPhoneScreenPreview() {
    NoteMarkTheme {
        Scaffold { padding ->
            LoginPhoneScreen(
                modifier = Modifier
                    .padding(top = padding.calculateTopPadding()),
                state = LoginState(),
                onEmailChanged = {},
                onPasswordChanged = {},
                onLoginClick = {},
                onRegisterClick = {}
            )
        }
    }
}