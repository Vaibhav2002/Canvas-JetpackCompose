package dev.vaibhav.canvasCompose.ui.screens.path

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
fun PathScreen(
    modifier: Modifier = Modifier
) {
    var isQuadCurve by remember { mutableStateOf(true) }
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        val bezierModifier = Modifier
            .fillMaxWidth()
            .weight(1f)
        if(isQuadCurve) QuadBezier(bezierModifier)
        else CubicBezier(bezierModifier)
        Box(modifier = Modifier.fillMaxHeight(0.2f), contentAlignment = Alignment.Center) {
            Button(onClick = { isQuadCurve = !isQuadCurve }) {
                Text(
                    text = if(isQuadCurve) "Cubic Bezier" else "Quad Bezier",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 32.dp)
                )
            }
        }

    }
}

@Composable
fun QuadBezier(modifier: Modifier = Modifier) {
    BoxWithConstraints(modifier = modifier) {
        val (height, width) = constraints.maxHeight.toFloat() to constraints.maxWidth.toFloat()
        val radius = 24.dp

        var point1 by remember { mutableStateOf(Offset(width / 4, height / 2)) }
        var point2 by remember { mutableStateOf(Offset(3 * width / 4, height / 2)) }
        var anchor by remember { mutableStateOf(Offset(width / 2, height / 4)) }

        var isDraggingPoint1 by remember { mutableStateOf(false) }
        var isDraggingPoint2 by remember { mutableStateOf(false) }
        var isDraggingAnchor by remember { mutableStateOf(false) }

        val pointColor = MaterialTheme.colorScheme.primary
        val anchorColor = MaterialTheme.colorScheme.tertiary
        val lineColor = MaterialTheme.colorScheme.onSurface

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(true) {
                    detectDragGestures(
                        onDragStart = {
                            when {
                                it.isInside(point1, radius.toPx()) -> isDraggingPoint1 = true
                                it.isInside(point2, radius.toPx()) -> isDraggingPoint2 = true
                                it.isInside(anchor, radius.toPx()) -> isDraggingAnchor = true
                            }
                        },
                        onDrag = { target, _ ->
                            val targetPoint = target.position
                            when {
                                isDraggingPoint1 -> point1 = targetPoint
                                isDraggingPoint2 -> point2 = targetPoint
                                isDraggingAnchor -> anchor = targetPoint
                            }
                        },
                        onDragEnd = {
                            isDraggingPoint1 = false
                            isDraggingPoint2 = false
                            isDraggingAnchor = false
                        }
                    )
                }
        ) {
            listOf(point1, point2).forEach {
                drawCircle(color = pointColor, center = it, radius = radius.toPx())
            }
            drawCircle(color = anchorColor, center = anchor, radius = radius.toPx())
            val path = Path().apply {
                moveTo(point1.x, point1.y)
                quadraticBezierTo(
                    x1 = anchor.x, y1 = anchor.y,
                    x2 = point2.x, y2 = point2.y
                )
            }
            drawPath(
                path = path,
                color = lineColor,
                style = Stroke(width = 4.dp.toPx())
            )

        }
    }

}

@Composable
fun CubicBezier(modifier: Modifier = Modifier) {
    BoxWithConstraints(modifier = modifier) {
        val (height, width) = constraints.maxHeight.toFloat() to constraints.maxWidth.toFloat()
        val radius = 24.dp

        var point1 by remember { mutableStateOf(Offset(width / 4, height / 2)) }
        var point2 by remember { mutableStateOf(Offset(3 * width / 4, height / 2)) }
        var anchor1 by remember { mutableStateOf(Offset(width / 3, height / 4)) }
        var anchor2 by remember { mutableStateOf(Offset(2*width / 3, height / 4)) }

        var isDraggingPoint1 by remember { mutableStateOf(false) }
        var isDraggingPoint2 by remember { mutableStateOf(false) }
        var isDraggingAnchor1 by remember { mutableStateOf(false) }
        var isDraggingAnchor2 by remember { mutableStateOf(false) }

        val pointColor = MaterialTheme.colorScheme.primary
        val anchorColor = MaterialTheme.colorScheme.tertiary
        val lineColor = MaterialTheme.colorScheme.onSurface

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(true) {
                    detectDragGestures(
                        onDragStart = {
                            when {
                                it.isInside(point1, radius.toPx()) -> isDraggingPoint1 = true
                                it.isInside(point2, radius.toPx()) -> isDraggingPoint2 = true
                                it.isInside(anchor1, radius.toPx()) -> isDraggingAnchor1 = true
                                it.isInside(anchor2, radius.toPx()) -> isDraggingAnchor2 = true
                            }
                        },
                        onDrag = { change, _ ->
                            val targetPoint = change.position
                            when {
                                isDraggingPoint1 -> point1 = targetPoint
                                isDraggingPoint2 -> point2 = targetPoint
                                isDraggingAnchor1 -> anchor1 = targetPoint
                                isDraggingAnchor2 -> anchor2 = targetPoint
                            }
                        },
                        onDragEnd = {
                            isDraggingPoint1 = false
                            isDraggingPoint2 = false
                            isDraggingAnchor1 = false
                            isDraggingAnchor2 = false
                        }
                    )
                }
        ) {
            listOf(point1, point2).forEach {
                drawCircle(color = pointColor, center = it, radius = radius.toPx())
            }
            listOf(anchor1, anchor2).forEach {
                drawCircle(color = anchorColor, center = it, radius = radius.toPx())
            }
            val path = Path().apply {
                moveTo(point1.x, point1.y)
                cubicTo(
                    x1 = anchor1.x, y1 = anchor1.y,
                    x2 = anchor2.x, y2 = anchor2.y,
                    x3 = point2.x, y3 = point2.y
                )
            }
            drawPath(
                path = path,
                color = lineColor,
                style = Stroke(width = 4.dp.toPx())
            )

        }
    }

}


private fun Offset.isInside(center: Offset, radius: Float): Boolean {
    return this distFrom center < radius
}

private infix fun Offset.distFrom(offset: Offset): Float {
    return sqrt((x - offset.x).pow(2) + (y - offset.y).pow(2))
}

@Preview
@Composable
fun PathScreenPreview() {
    PathScreen(modifier = Modifier.fillMaxSize())
}