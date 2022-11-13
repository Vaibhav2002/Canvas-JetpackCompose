package dev.vaibhav.canvasCompose.ui.screens.transformations

import android.animation.TimeInterpolator
import android.view.animation.BounceInterpolator
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.translate

@Composable
fun TranslateCanvas(
    durationMillis:Int,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier = modifier) {
        val xPos = remember { Animatable(initialValue = 0f) }
        val yPos = remember { Animatable(initialValue = 0f) }
        val boxSize = Size(200f, 200f)
        val rectPath = Path().apply {
            addRect(Rect(offset = Offset(0f, 0f), size = boxSize))
        }
        val boxColor = MaterialTheme.colorScheme.primary
        LaunchedEffect(key1 = true) {
            xPos.animate(durationMillis, constraints.maxWidth.toFloat() - boxSize.width - 100f)
        }
        LaunchedEffect(key1 = true) {
            yPos.animate(durationMillis, constraints.maxHeight.toFloat() - boxSize.height - 100f)
        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            translate(left = xPos.value, top = yPos.value) {
                drawPath(
                    path = rectPath,
                    color = boxColor
                )
            }
        }
    }
}

private suspend fun Animatable<Float, AnimationVector1D>.animate(durationMillis: Int, target: Float) {
    animateTo(
        target,
        animationSpec = tween(
            durationMillis = durationMillis,
            easing = BounceInterpolator().toEasing()
        )
    )
}

private fun TimeInterpolator.toEasing() = Easing {
    getInterpolation(it)
}