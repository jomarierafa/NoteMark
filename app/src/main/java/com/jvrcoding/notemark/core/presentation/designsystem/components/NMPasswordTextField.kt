package com.jvrcoding.notemark.core.presentation.designsystem.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jvrcoding.notemark.core.presentation.designsystem.theme.EyeClosedIcon
import com.jvrcoding.notemark.core.presentation.designsystem.theme.EyeOpenIcon
import com.jvrcoding.notemark.core.presentation.designsystem.theme.NoteMarkTheme

@Composable
fun NMPasswordTextField(
    modifier: Modifier = Modifier,
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    supportingText: String = "",
    errorText: String = "",
    isError: Boolean = false,
    imeAction: ImeAction = ImeAction.Next
) {
    var passwordVisible by remember { mutableStateOf(false) }

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val passwordVisualTransformation = remember { PasswordVisualTransformation(mask = '*') }
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(4.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            interactionSource = interactionSource,
            placeholder = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            supportingText = {
                if(errorText.isNotEmpty() && (!isFocused && isError)) {
                    Text(
                        text = errorText,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(bottom = 7.dp)
                    )
                } else if(supportingText.isNotEmpty() && isFocused) {
                    Text(
                        text = supportingText,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(bottom = 7.dp)
                    )
                }

            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = imeAction
            ),
            visualTransformation = if (passwordVisible) VisualTransformation.None else passwordVisualTransformation,
            trailingIcon = {
                val icon = if (passwordVisible) EyeClosedIcon else  EyeOpenIcon
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = icon, contentDescription = if (passwordVisible) "Hide password" else "Show password")
                }
            },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
            isError = !isFocused && isError,
            maxLines = 1,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = Color.Transparent,
                errorBorderColor = MaterialTheme.colorScheme.error,
                errorSupportingTextColor = MaterialTheme.colorScheme.error,
                focusedContainerColor = Color.White,
                errorContainerColor =  MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                errorTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
    }
}

@Preview
@Composable
private fun NMPasswordTextFieldPreview() {
    NoteMarkTheme {
        NMPasswordTextField(
            label = "Username",
            placeholder = "Enter username",
            value = "23232",
            onValueChange = {  },
            isError = false
        )
    }
}