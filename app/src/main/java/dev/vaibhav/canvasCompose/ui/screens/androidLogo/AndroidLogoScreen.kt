package dev.vaibhav.canvasCompose.ui.screens.androidLogo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun AndroidLogoScreen(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center){
        AndroidLogo(modifier = Modifier.fillMaxWidth(0.8f).aspectRatio(1.77f))
    }
}