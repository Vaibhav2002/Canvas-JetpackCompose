package dev.vaibhav.canvasCompose.ui.screens.transformations

import android.animation.TimeInterpolator
import android.view.animation.BounceInterpolator
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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate

@Composable
fun AllTransformationCanvas(
    translateDuration: Int,
    rotateDuration:Int,
    scaleDuration:Int,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier = modifier) {
        val boxSize = 200f
        val rectPath = Path().apply {
            addRect(Rect(offset = Offset(0f, 0f), size = Size(200f, 200f)))
        }
        val boxColor = MaterialTheme.colorScheme.primary

        //translation
        val translationTransition = rememberInfiniteTransition()
        val xPos by translationTransition.animateFloat(
            initialValue = boxSize+100f,
            targetValue = constraints.maxWidth.toFloat() - boxSize - 100f,
            animationSpec = infiniteRepeatable(
                animation = tween(translateDuration, easing = BounceInterpolator().toEasing()),
                repeatMode = RepeatMode.Reverse
            )
        )
        val yPos by translationTransition.animateFloat(
            initialValue = boxSize+100f,
            targetValue = constraints.maxHeight.toFloat() - boxSize - 100f,
            animationSpec = infiniteRepeatable(
                animation = tween(translateDuration, easing = BounceInterpolator().toEasing()),
                repeatMode = RepeatMode.Reverse
            )
        )

        //rotation
        val transition = rememberInfiniteTransition()
        val angle by transition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(rotateDuration, easing = LinearOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            )
        )

        //scaling
        val scaleTransition = rememberInfiniteTransition()
        val scale by scaleTransition.animateFloat(
            initialValue = 1f,
            targetValue = 2f,
            animationSpec = infiniteRepeatable(
                animation = tween(scaleDuration, easing = LinearOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            )
        )

        Canvas(modifier = Modifier.fillMaxSize()) {
            translate(left = xPos, top = yPos) {
                val boxCenter = Offset(
                    x = boxSize/2f,
                    y = boxSize/2f
                )
                rotate(angle, boxCenter){
                    scale(scale, boxCenter){
                        drawPath(
                            path = rectPath,
                            color = boxColor
                        )
                    }
                }
            }
        }
    }
}

private fun TimeInterpolator.toEasing() = Easing {
    getInterpolation(it)
}