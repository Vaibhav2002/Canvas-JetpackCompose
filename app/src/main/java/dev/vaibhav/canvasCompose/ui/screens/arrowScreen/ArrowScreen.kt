package dev.vaibhav.canvasCompose.ui.screens.arrowScreen

import android.graphics.PathMeasure
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.atan2

@Composable
fun ArrowScreen(
    modifier: Modifier = Modifier
) {
    val path = Path().apply {
        moveTo(0f, 0f)
        quadraticBezierTo(1900f, 1600f, 400f, 2000f)
    }
    val pathPortion = remember { Animatable(initialValue = 0f) }
    val scope = rememberCoroutineScope()

    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary

    val tan = FloatArray(2)
    val endPos = FloatArray(2)

    val outPath = Path()
    PathMeasure(path.asAndroidPath(), false).apply {
        getSegment(0f, length * pathPortion.value, outPath.asAndroidPath(), true)
        getPosTan(length * pathPortion.value, endPos, tan)
    }
    Canvas(modifier = modifier.pointerInput(true) {
        detectTapGestures {
            scope.launch { startAnimation(pathPortion) }
        }
    }) {
        drawPath(
            outPath,
            color = primaryColor,
            style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round),
        )

        val (x, y) = endPos
        val degrees = -atan2(tan[0], tan[1]).toDegree() - 180f

        rotate(degrees, Offset(x, y)) {
            val arrowHeadPath = Path().apply {
                moveTo(x, y - 30f)
                lineTo(x - 50f, y + 90f)
                lineTo(x, y + 30)
                lineTo(x + 50, y + 90)
                close()
            }
            drawPath(arrowHeadPath, color = secondaryColor)
        }

    }
}

private fun Float.toDegree() = this * 180 / Math.PI.toFloat()

private suspend fun startAnimation(animatable: Animatable<Float, AnimationVector1D>) {
    animatable.snapTo(0f)
    animatable.animateTo(
        targetValue = 1f,
        animationSpec = tween(5000, easing = FastOutSlowInEasing)
    )
}