package com.jvrcoding.notemark.auth.presentation.register.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jvrcoding.notemark.R
import com.jvrcoding.notemark.auth.presentation.register.RegisterState
import com.jvrcoding.notemark.auth.presentation.register.components.RegisterFieldSection
import com.jvrcoding.notemark.core.presentation.designsystem.components.NMHeader
import com.jvrcoding.notemark.core.presentation.designsystem.theme.NoteMarkTheme

@Composable
fun RegisterPhoneScreen(
    modifier: Modifier = Modifier,
    state: RegisterState,
    onUsernameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onConfirmPasswordChanged: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit
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
            .verticalScroll(rememberScrollState())
    ) {
        NMHeader(headerText = stringResource(R.string.create_account))
        Spacer(modifier = Modifier.height(24.dp))
        RegisterFieldSection(
            state = state,
            onUsernameChanged = { onUsernameChanged(it) },
            onEmailChanged = { onEmailChanged(it) },
            onPasswordChanged = { onPasswordChanged(it) },
            onConfirmPasswordChanged = { onConfirmPasswordChanged(it) },
            onRegisterClick = { onRegisterClick() },
            onLoginClick = { onLoginClick() },
        )
    }
}

@Preview
@Composable
private fun RegisterPhoneScreenPreview() {
    NoteMarkTheme {
        RegisterPhoneScreen(
            state = RegisterState(),
            onRegisterClick = {},
            onLoginClick = {},
            onUsernameChanged = {},
            onEmailChanged = {},
            onPasswordChanged = {},
            onConfirmPasswordChanged = {}
        )
    }
}