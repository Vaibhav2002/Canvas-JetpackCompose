package dev.vaibhav.canvasCompose.ui.screens.circularProgress

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.vaibhav.canvasCompose.ui.screens.shapes.arc

data class ProgressColors(
    val start: Color,
    val mid: Color,
    val end: Color
)

@Composable
fun CircularProgressBar(
    progress: Float,
    colors: ProgressColors,
    modifier: Modifier = Modifier
) {
    val color = animateColorAsState(
        targetValue = progress.getColorFromProgress(colors),
        animationSpec = tween(1000, easing = LinearEasing)
    )
    val angle = 360f * progress
    Canvas(modifier = modifier) {
        arc(
            color = color.value,
            offset = Offset(0f, 0f),
            angle = angle,
            strokeWidth = 24.dp.toPx(),
            size = size
        )
    }
}

private fun Float.getColorFromProgress(color: ProgressColors) = when {
    this < .4 -> color.start
    this < .75 -> color.mid
    else -> color.end
}
