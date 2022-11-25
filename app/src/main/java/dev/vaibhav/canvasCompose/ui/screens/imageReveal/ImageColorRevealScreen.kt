package dev.vaibhav.canvasCompose.ui.screens.imageReveal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.vaibhav.canvasCompose.R

@Composable
fun ImageColorRevealScreen(
    modifier:Modifier = Modifier
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center){
        ImageColorReveal(
            modifier = Modifier.fillMaxWidth().background(Color.Red),
            imageRes = R.drawable.landscape,
            circleRadius = 64.dp
        )
    }
}