package com.jvrcoding.notemark.core.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jvrcoding.notemark.ui.theme.NoteMarkTheme

@Composable
fun NMTextField(
    modifier: Modifier = Modifier,
    label: String,
    placeholder: String,
    value: String,
    supportingText: String? = null,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
) {

    Column(
        modifier = modifier
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(4.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            supportingText = {
                if(!supportingText.isNullOrBlank()) {
                    Text(
                        text = "Supporting text",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
            ),
            modifier = Modifier
                .fillMaxWidth(),
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
            isError = isError,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = Color.Transparent,
                errorBorderColor = MaterialTheme.colorScheme.error,
                errorSupportingTextColor =  MaterialTheme.colorScheme.error,
                focusedContainerColor = Color.White,
                errorContainerColor = Color.White,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface
            )
        )
    }
}

@Preview
@Composable
private fun SampleButtonPreview() {
    NoteMarkTheme {
        NMTextField(
            label = "Username",
            placeholder = "Enter username",
            value = "",
            onValueChange = {  },
            isError = false
        )
    }
}