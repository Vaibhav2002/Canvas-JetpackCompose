package dev.vaibhav.canvasCompose.ui.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke

fun DrawScope.rectangle(offset: Offset, size: Size) {
    drawRect(
        color = Color.Red,
        topLeft = offset,
        size = size,
    )
}

fun DrawScope.square(offset: Offset, size: Size) {
    drawRect(
        color = Color.Blue,
        topLeft = offset,
        size = size,
    )
}

fun DrawScope.circle(center: Offset, radius: Float) {
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(Color.Cyan, Color.Blue),
            center = center,
            radius = radius
        ),
        radius = radius,
        center = center,
    )
}

fun DrawScope.arc(
    color: Color,
    offset: Offset,
    angle: Float,
    strokeWidth: Float,
    size: Size
) {
    drawArc(
        color = color,
        startAngle = 0f,
        sweepAngle = angle,
        useCenter = false,
        size = size,
        topLeft = offset,
        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
    )
}
