package com.jvrcoding.notemark.auth.presentation.login.layout

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
import com.jvrcoding.notemark.auth.presentation.login.components.LoginFieldSection
import com.jvrcoding.notemark.core.presentation.NMHeader
import com.jvrcoding.notemark.ui.theme.ExtraLargeTitle
import com.jvrcoding.notemark.ui.theme.NoteMarkTheme

@Composable
fun LoginTabletScreen(
    modifier: Modifier = Modifier,
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
                horizontal = 120.dp,
                vertical = 100.dp
            )
    ) {
        NMHeader(
            headerText = stringResource(R.string.log_in),
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            headerTextStyle = ExtraLargeTitle
        )
        Spacer(modifier = Modifier.height(32.dp))
        LoginFieldSection(
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
private fun LoginTabletScreenPreview() {
    NoteMarkTheme {
        Scaffold { padding ->
            LoginTabletScreen(
                modifier = Modifier.padding(top = padding.calculateTopPadding()),
                onLoginClick = {},
                onRegisterClick = {}
            )
        }
    }
}