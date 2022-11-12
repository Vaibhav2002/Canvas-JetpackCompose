package dev.vaibhav.canvasCompose.ui.screens.androidLogo

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AndroidLogo(
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val (height, width) = size.height to size.width
        val logoColor = Color(0xFF00e27a)
        val curveXPercentage = 0.1f
        val path = Path().apply {
            moveTo(0f, height)
            lineTo(width, height)
            cubicTo(
                x1 = width - curveXPercentage * width, y1 = 0f,
                x2 = curveXPercentage * width, y2 = 0f,
                x3 = 0f, y3 = height
            )
        }
        drawPath(path, color = logoColor)
        drawEyes()
    }
}

fun DrawScope.drawEyes() {
    val (height, width) = size.height to size.width
    val (eyesXPercentage, eyesYPercentage) = 0.28f to 0.65f
    val eyesRadius = 12.dp.toPx()
    drawCircle(
        color = Color.White,
        radius = eyesRadius,
        center = Offset(width * eyesXPercentage, height * eyesYPercentage)
    )
    drawCircle(
        color = Color.White,
        radius = eyesRadius,
        center = Offset(width - width * eyesXPercentage, height * eyesYPercentage)
    )

}

@Preview
@Composable
fun AndroidLogoPreview() {
    Box(
        modifier = Modifier
            .aspectRatio(1.77f),
        contentAlignment = Alignment.Center
    ) {
        AndroidLogo(
            modifier = Modifier.fillMaxSize(0.8f)
        )
    }

}