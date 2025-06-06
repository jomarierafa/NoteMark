package com.jvrcoding.notemark.auth.presentation.landing.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jvrcoding.notemark.R
import com.jvrcoding.notemark.auth.presentation.landing.components.LandingMenu
import com.jvrcoding.notemark.ui.theme.NoteMarkTheme
import com.jvrcoding.notemark.ui.theme.SoftBlue

@Composable
fun LandingPhoneLandscapeScreen(
    modifier: Modifier = Modifier,
    onGetStartedClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .background(SoftBlue)
    ) {
        Image(
            painter = painterResource(R.drawable.landing_image),
            contentDescription = "Landing Image",
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxHeight()
                .width(378.dp),
            contentScale = ContentScale.Crop
        )

        LandingMenu(
            modifier = Modifier
                .height(330.dp)
                .weight(1f)
                .align(Alignment.CenterVertically)
                .clip(RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerLowest)
                .padding(40.dp),
            onGetStartedClick = { onGetStartedClick() },
            onLoginClick = { onLoginClick() }
        )
    }
}

@Preview(name = "Phone - Landscape",
    device = "spec:width = 411dp, height = 891dp, orientation = landscape, dpi = 420",
    showSystemUi = true)
@Composable
private fun LandingPhoneLandscapeScreenPreview() {
    NoteMarkTheme {
//        LandingPhoneLandscapeScreen()
    }
}