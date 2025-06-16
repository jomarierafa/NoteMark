package com.jvrcoding.notemark.auth.presentation.login.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jvrcoding.notemark.R
import com.jvrcoding.notemark.auth.presentation.login.LoginState
import com.jvrcoding.notemark.core.presentation.components.NMActionButton
import com.jvrcoding.notemark.core.presentation.components.NMPasswordTextField
import com.jvrcoding.notemark.core.presentation.components.NMTextField
import com.jvrcoding.notemark.ui.theme.NoteMarkTheme

@Composable
fun LoginFieldSection(
    modifier: Modifier = Modifier,
    state: LoginState,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {

    Column(modifier = modifier) {
        NMTextField(
            label = stringResource(R.string.email),
            placeholder = stringResource(R.string.john_doe_example_com),
            value = state.email,
            onValueChange = { onEmailChanged(it) },
        )


        NMPasswordTextField(
            label = stringResource(R.string.password),
            placeholder = stringResource(R.string.password),
            value = state.password,
            onValueChange = { onPasswordChanged(it) },
            imeAction = ImeAction.Done
        )

        Spacer(modifier = Modifier.height(12.dp))

        NMActionButton(
            text = stringResource(R.string.log_in),
            isLoading = state.isLoggingIn,
            enabled = state.canLogin,
            onClick = { onLoginClick() }
        )

        Spacer(modifier = Modifier.height(8.dp))

        NMActionButton(
            text = stringResource(R.string.don_t_have_an_account),
            isLoading = false,
            buttonColors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onPrimary,
                contentColor = MaterialTheme.colorScheme.primary,
            ),
            onClick = { onRegisterClick() }
        )

    }
}

@Preview
@Composable
private fun LoginFieldSectionPreview() {
    NoteMarkTheme {
        LoginFieldSection(
            modifier = Modifier.background(Color.White),
            state = LoginState(),
            onEmailChanged = {},
            onPasswordChanged = {},
            onLoginClick =  {},
            onRegisterClick = {}
        )
    }
}