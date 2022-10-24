package dev.vaibhav.canvasCompose.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.vaibhav.canvasCompose.ui.components.CircularProgressBar
import dev.vaibhav.canvasCompose.ui.components.ProgressColors
import kotlinx.coroutines.launch

@Composable
fun CircularProgressScreen(
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val progress = remember { Animatable(0f) }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressBar(
            progress = progress.value,
            colors = ProgressColors(Color.Red, Color.Yellow, Color.Green),
            modifier = Modifier.fillMaxWidth(0.7f).aspectRatio(1f)
        )
        Spacer(modifier = Modifier.height(32.dp))
        AnimationButton(text = "Play") {
            scope.launch { animate(progress) }
        }
        Spacer(modifier = Modifier.height(16.dp))
        AnimationButton(text = "Play Reverse") {
            scope.launch { animate(progress, true) }
        }
    }
}

@Composable
private fun AnimationButton(
    text: String,
    onClick: () -> Unit
) {
    OutlinedButton(onClick = onClick) {
        Text(text = text, style = MaterialTheme.typography.titleMedium)
    }
}

private suspend fun animate(
    progress: Animatable<Float, AnimationVector1D>,
    reverse: Boolean = false
) {
    val start = if (!reverse) 0f else 1f
    val end = 1 - start
    progress.snapTo(start)
    progress.animateTo(end, animationSpec = tween(10 * 1000, easing = FastOutSlowInEasing))
}
