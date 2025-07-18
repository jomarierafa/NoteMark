package com.jvrcoding.notemark.auth.presentation.register.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jvrcoding.notemark.R
import com.jvrcoding.notemark.auth.presentation.register.RegisterState
import com.jvrcoding.notemark.auth.presentation.register.components.RegisterFieldSection
import com.jvrcoding.notemark.core.presentation.designsystem.components.NMHeader
import com.jvrcoding.notemark.core.presentation.designsystem.theme.ExtraLargeTitle
import com.jvrcoding.notemark.core.presentation.designsystem.theme.NoteMarkTheme

@Composable
fun RegisterTabletScreen(
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
                horizontal = 120.dp,
                vertical = 100.dp
            )
    ) {
        NMHeader(
            headerText = stringResource(R.string.create_account),
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            headerTextStyle = ExtraLargeTitle
        )
        Spacer(modifier = Modifier.height(32.dp))
        RegisterFieldSection(
            state = state,
            onUsernameChanged = { onUsernameChanged(it) },
            onEmailChanged = { onEmailChanged(it) },
            onPasswordChanged = { onPasswordChanged(it) },
            onConfirmPasswordChanged = { onConfirmPasswordChanged(it) },
            onRegisterClick = { onRegisterClick() },
            onLoginClick = { onLoginClick() }
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
private fun RegisterTabletScreenPreview() {
    NoteMarkTheme {
        Scaffold { padding ->
            RegisterTabletScreen(
                state = RegisterState(),
                modifier = Modifier.padding(top = padding.calculateTopPadding()),
                onUsernameChanged = {},
                onEmailChanged = {},
                onPasswordChanged = {},
                onConfirmPasswordChanged = {},
                onRegisterClick = {},
                onLoginClick = {}
            )
        }
    }
}