package dev.vaibhav.canvasCompose.ui.screens.clock

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

private sealed interface SpokeType {
    object MinutesSpoke : SpokeType
    object Minutes5Spoke : SpokeType
}

private sealed interface HandType {
    object HourHand : HandType
    object MinutesHand : HandType
    object SecondsHand : HandType
}

data class ClockColors(
    val fiveSpokeColor: Color = Color.Black,
    val spokeColor: Color = Color.DarkGray,
    val secondsHandColor: Color = Color.Red,
    val minutesHandColor: Color = Color.DarkGray,
    val hoursHandColor: Color = Color.Black,
    val backgroundColor: Color = Color.White,
)

data class SpokeSize(
    val fiveSpokeLength: Dp = 16.dp,
    val spokeLength: Dp = 10.dp,
    val fiveSpokeWidth: Dp = 1.dp,
    val spokeWidth: Dp = 0.5.dp
)

@Composable
fun Clock(
    hours:Float,
    minutes:Float,
    seconds:Float,
    size: Dp,
    modifier: Modifier = Modifier,
    colors: ClockColors = ClockColors(),
    spokeSizes: SpokeSize = SpokeSize()
) {
    Canvas(modifier = modifier.size(size)) {
        drawContext.canvas.nativeCanvas.apply {
            drawCircle(
                center.x,
                center.y,
                size.div(2).toPx(),
                Paint().apply {
                    color = android.graphics.Color.WHITE
                    setShadowLayer(
                        50f,
                        0f,
                        0f,
                        android.graphics.Color.argb(200f, 0f, 0f, 0f)
                    )
                }
            )
        }


        drawSpokes(colors, spokeSizes)

        drawHand(
            angle = hours.times(30),
            handType = HandType.HourHand,
            spokeSizes = spokeSizes,
            color = colors.hoursHandColor
        )
        drawHand(
            angle = minutes.times(6),
            handType = HandType.MinutesHand,
            spokeSizes = spokeSizes,
            color = colors.minutesHandColor
        )
        drawHand(
            angle = seconds.times(6),
            handType = HandType.SecondsHand,
            spokeSizes = spokeSizes,
            color = colors.secondsHandColor
        )
    }
}

fun DrawScope.drawSpokes(colors: ClockColors, spokeSizes: SpokeSize) {
    for (angle in 0..360 step 6) {
        val spokeType = when {
            angle % 30 == 0 -> SpokeType.Minutes5Spoke
            else -> SpokeType.MinutesSpoke
        }
        drawSpoke(
            angle = angle,
            spokeType = spokeType,
            spokeSizes = spokeSizes,
            colors = colors
        )
    }
}

private fun DrawScope.drawSpoke(
    angle: Int,
    spokeType: SpokeType,
    spokeSizes: SpokeSize,
    colors: ClockColors
) {
    val radius = size.height.div(2)
    val angleInRad = angle.toRadian()
    val spokeLength = when (spokeType) {
        SpokeType.Minutes5Spoke -> spokeSizes.fiveSpokeLength
        SpokeType.MinutesSpoke -> spokeSizes.spokeLength
    }
    val spokeColor = when (spokeType) {
        SpokeType.Minutes5Spoke -> colors.fiveSpokeColor
        SpokeType.MinutesSpoke -> colors.spokeColor
    }
    val spokeWidth = when (spokeType) {
        SpokeType.Minutes5Spoke -> spokeSizes.fiveSpokeWidth
        SpokeType.MinutesSpoke -> spokeSizes.spokeWidth
    }

    val spokeTop = Offset(
        x = radius * cos(angleInRad).toFloat() + center.x,
        y = radius * sin(angleInRad).toFloat() + center.y
    )
    val spokeBottom = Offset(
        x = (radius - spokeLength.toPx()) * cos(angleInRad).toFloat() + center.x,
        y = (radius - spokeLength.toPx()) * sin(angleInRad).toFloat() + center.y
    )

    drawLine(
        color = spokeColor,
        start = spokeTop,
        end = spokeBottom,
        strokeWidth = spokeWidth.toPx()
    )
}

private fun DrawScope.drawHand(
    angle: Float,
    handType: HandType,
    spokeSizes: SpokeSize,
    color:Color
) {
    val distFromCircumference = when (handType) {
        HandType.HourHand -> spokeSizes.fiveSpokeLength.plus(20.dp).toPx()
        HandType.MinutesHand -> spokeSizes.spokeLength.plus(12.dp).toPx()
        HandType.SecondsHand -> spokeSizes.spokeLength.plus(8.dp).toPx()
    }
    val width = when (handType) {
        HandType.HourHand -> 3.dp.toPx()
        HandType.MinutesHand -> 2.dp.toPx()
        HandType.SecondsHand -> 1.dp.toPx()
    }
    rotate(degrees = angle){
        drawLine(
            color = color,
            start = center,
            end = Offset(center.x, distFromCircumference),
            strokeWidth = width,
            cap = StrokeCap.Round
        )
    }
}

private fun Int.toRadian() = this * Math.PI / 180
private fun Int.toDegree() = this * 180 / Math.PI

@Preview
@Composable
fun ClockPreview() {
    Clock(2.3f, 45f, 30f, size = 200.dp)
}