package dev.vaibhav.canvasCompose.ui.screens.imageReveal

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize

@Composable
fun ImageColorReveal(
    modifier: Modifier = Modifier,
    @DrawableRes imageRes: Int,
    circleRadius: Dp,
) {
    val image = ImageBitmap.imageResource(id = imageRes)
    val imageAspectRatio = remember { image.width.toFloat() / image.height }
    var center by remember { mutableStateOf(Offset.Zero) }

    Canvas(
        modifier = modifier.aspectRatio(imageAspectRatio)
            .pointerInput(true) {
            detectDragGestures { change, _ ->
                center = change.position
            }
        }
    ) {
        val circlePath = Path().apply {
            addOval(Rect(center = center, radius = circleRadius.toPx()))
        }

        val imageSize =
            IntSize(width = size.width.toInt(), height = size.height.toInt())
        drawImage(
            image = image,
            dstOffset = IntOffset.Zero,
            dstSize = imageSize,
            colorFilter = ColorFilter.tint(color = Color.Black, blendMode = BlendMode.Color)
        )

        clipPath(path = circlePath, clipOp = ClipOp.Intersect) {
            drawImage(
                image = image,
                dstOffset = IntOffset.Zero,
                dstSize = imageSize
            )
        }
    }
}