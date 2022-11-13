package dev.vaibhav.canvasCompose.ui.screens.transformations

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.scale

@Composable
fun ScalingCanvas(
    durationMillis:Int,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier = modifier) {
        val boxSize = 100f
        val center = Offset(constraints.maxWidth/2f, constraints.maxHeight/2f)
        val rectPath = Path().apply {
            addRect(Rect(center = center, radius = boxSize))
        }
        val boxColor = MaterialTheme.colorScheme.primary
        val transition = rememberInfiniteTransition()
        val scale by transition.animateFloat(
            initialValue = 1f,
            targetValue = 3f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            )
        )

        Canvas(modifier = Modifier.fillMaxSize()) {
            scale(scale) {
                drawPath(
                    path = rectPath,
                    color = boxColor
                )
            }
        }
    }
}