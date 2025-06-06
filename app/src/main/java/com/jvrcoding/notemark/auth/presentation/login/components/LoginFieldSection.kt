package com.jvrcoding.notemark.auth.presentation.login.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jvrcoding.notemark.R
import com.jvrcoding.notemark.core.presentation.NMActionButton
import com.jvrcoding.notemark.core.presentation.NMPasswordTextField
import com.jvrcoding.notemark.core.presentation.NMTextField
import com.jvrcoding.notemark.ui.theme.NoteMarkTheme

@Composable
fun LoginFieldSection(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    var text by remember { mutableStateOf("") }

    Column(modifier = modifier) {
        NMTextField(
            label = stringResource(R.string.email),
            placeholder = stringResource(R.string.john_doe_example_com),
            value = text,
            onValueChange = {
                text = it
            }
        )


        NMPasswordTextField(
            label = stringResource(R.string.password),
            placeholder = stringResource(R.string.password),
            value = text,
            onValueChange = {
                text = it
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        NMActionButton(
            text = stringResource(R.string.log_in),
            isLoading = false,
            enabled = false,
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
            onLoginClick =  {},
            onRegisterClick = {}
        )
    }
}