package com.jvrcoding.notemark.auth.presentation.landing

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import com.jvrcoding.notemark.ui.theme.ExtraLargeTitle
import com.jvrcoding.notemark.ui.theme.NoteMarkTheme

@Composable
fun LandingTabletScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.landing_image_tablet),
            contentDescription = "Landing Image",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
        )

        LandingMenu(
            modifier = Modifier
                .width(680.dp)
                .height(314.dp)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerLowest)
                .padding(48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            headerTextStyle = ExtraLargeTitle
        )
    }
}

@Preview(
    name = "Tablet Portrait",
    showBackground = true,
    widthDp = 800,
    heightDp = 1280
)
@Composable
private fun LandingTabletScreenPreview() {
    NoteMarkTheme {
        LandingTabletScreen()
    }
}