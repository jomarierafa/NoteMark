package com.jvrcoding.notemark.auth.presentation.register.components

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
import com.jvrcoding.notemark.auth.presentation.register.RegisterState
import com.jvrcoding.notemark.core.presentation.designsystem.components.NMActionButton
import com.jvrcoding.notemark.core.presentation.designsystem.components.NMPasswordTextField
import com.jvrcoding.notemark.core.presentation.designsystem.components.NMTextField
import com.jvrcoding.notemark.core.presentation.designsystem.theme.NoteMarkTheme

@Composable
fun RegisterFieldSection(
    state: RegisterState,
    modifier: Modifier = Modifier,
    onUsernameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onConfirmPasswordChanged: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit
) {

    Column(modifier = modifier) {

        NMTextField(
            label = stringResource(R.string.username),
            placeholder = stringResource(R.string.john_doe),
            value = state.username,
            onValueChange = { onUsernameChanged(it) },
            isError = state.shouldShowUsernameError,
            supportingText = stringResource(R.string.use_between_3_and_20_characters_for_your_username),
            errorText = stringResource(R.string.username_error_message)

        )
        NMTextField(
            label = stringResource(R.string.email),
            placeholder = stringResource(R.string.john_doe_example_com),
            value = state.email,
            onValueChange =  { onEmailChanged(it) },
            isError = state.shouldShowEmailError,
            errorText = stringResource(R.string.invalid_email_provided)
        )
        NMPasswordTextField(
            label = stringResource(R.string.password),
            placeholder = stringResource(R.string.password),
            value = state.password,
            onValueChange = { onPasswordChanged(it) },
            isError = state.shouldShowPasswordError,
            supportingText = stringResource(R.string.password_supporting_text),
            errorText = stringResource(R.string.password_must_be)
        )
        NMPasswordTextField(
            label = stringResource(R.string.repeat_password),
            placeholder = stringResource(R.string.password),
            value = state.confirmPassword,
            onValueChange = { onConfirmPasswordChanged(it) },
            isError = state.shouldShowConfirmPasswordError,
            errorText = stringResource(R.string.passwords_do_not_match),
            imeAction = ImeAction.Done
        )

        Spacer(modifier = Modifier.height(12.dp))

        NMActionButton(
            text = stringResource(R.string.create_account),
            isLoading = state.isRegistering,
            enabled = state.canRegister,
            onClick = { onRegisterClick() }
        )
        Spacer(modifier = Modifier.height(8.dp))
        NMActionButton(
            text = "Already have an account?",
            isLoading = false,
            buttonColors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onPrimary,
                contentColor = MaterialTheme.colorScheme.primary,
            ),
            onClick = { onLoginClick() }
        )

    }
}

@Preview
@Composable
private fun RegisterFieldSectionPreview() {
    NoteMarkTheme {
        RegisterFieldSection(
            state = RegisterState(),
            modifier = Modifier.background(Color.White),
            onRegisterClick = {},
            onLoginClick = {},
            onUsernameChanged = {},
            onEmailChanged = {},
            onPasswordChanged = {},
            onConfirmPasswordChanged = {},
        )
    }
}