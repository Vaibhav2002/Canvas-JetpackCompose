package dev.vaibhav.canvasCompose.ui.screens.genderPicker

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.vaibhav.canvasCompose.R

@Preview(showBackground = true)
@Composable
fun GenderPickerPreview() {
    GenderPicker(
        modifier = Modifier.fillMaxSize(),
    ) {

    }
}

@Composable
fun GenderPicker(
    modifier: Modifier = Modifier,
    scalingFactor: Float = 12f,
    distanceBetween: Dp = 54.dp,
    maleColors:List<Color> = listOf(Color.Blue, Color.Magenta),
    femaleColors:List<Color> = listOf(Color.Red, Color.Magenta),
    onGenderChange: (Gender) -> Unit
) {
    var selectedGender by remember { mutableStateOf<Gender>(Gender.Male) }

    val malePathString = stringResource(id = R.string.male_path)
    val femalePathString = stringResource(id = R.string.female_path)
    val malePath = remember { getPath(malePathString) }
    val femalePath = remember { getPath(femalePathString) }
    val maleBounds = remember { malePath.getBounds() }
    val femaleBounds = remember { femalePath.getBounds() }
    var center by remember { mutableStateOf(Offset.Unspecified) }

    var maleTransformedOffset by remember {
        mutableStateOf(Offset.Zero)
    }
    var femaleTransformedOffset by remember {
        mutableStateOf(Offset.Zero)
    }
    var clickPosition by remember {
        mutableStateOf(Offset.Zero)
    }
    val maleRadius = animateFloatAsState(
        targetValue = if(selectedGender is Gender.Male) 70f else 0f,
        animationSpec = tween(500, easing = LinearEasing)
    )

    val femaleRadius = animateFloatAsState(
        targetValue = if(selectedGender is Gender.Female) 70f else 0f,
        animationSpec = tween(500, easing = LinearEasing)
    )

    Canvas(
        modifier = modifier.pointerInput(true) {
            detectTapGestures {
                val transformedMaleBounds = Rect(
                    offset = maleTransformedOffset,
                    size = maleBounds.size * scalingFactor
                )

                val transformedFemaleBounds = Rect(
                    offset = femaleTransformedOffset,
                    size = maleBounds.size * scalingFactor
                )

                if (selectedGender !is Gender.Male && transformedMaleBounds.contains(it)) {
                    selectedGender = Gender.Male
                    onGenderChange(Gender.Male)
                    clickPosition = it
                } else if (selectedGender !is Gender.Female && transformedFemaleBounds.contains(it)) {
                    selectedGender = Gender.Female
                    onGenderChange(Gender.Female)
                    clickPosition = it
                }
            }
        }
    ) {
        center = this.center

        maleTransformedOffset = Offset(
            x = center.x - maleBounds.width * scalingFactor - distanceBetween.toPx() / 2f,
            y = center.y - maleBounds.height * scalingFactor / 2f
        )
        femaleTransformedOffset = Offset(
            x = center.x + distanceBetween.toPx() / 2f,
            y = center.y - scalingFactor * femaleBounds.height / 2f
        )

        val untransformedMaleClickOffset = getUnTransformedClickOffset(
            clickOffset = clickPosition,
            bounds = maleBounds,
            offset = maleTransformedOffset,
            scalingFactor = scalingFactor
        )
        val untransformedFemaleClickOffset = getUnTransformedClickOffset(
            clickOffset = clickPosition,
            bounds = femaleBounds,
            offset = femaleTransformedOffset,
            scalingFactor = scalingFactor
        )

        drawGender(
            path = malePath,
            offset = maleTransformedOffset,
            bounds = maleBounds,
            scalingFactor = scalingFactor,
            color = Color.LightGray,
            clickedOffset = untransformedMaleClickOffset,
            radius = maleRadius.value,
            gradientColors = maleColors
        )

        drawGender(
            path = femalePath,
            offset = femaleTransformedOffset,
            bounds = femaleBounds,
            scalingFactor = scalingFactor,
            color = Color.LightGray,
            clickedOffset = untransformedFemaleClickOffset,
            radius = femaleRadius.value,
            gradientColors = femaleColors
        )
    }
}

fun DrawScope.drawGender(
    path: Path,
    offset: Offset,
    bounds: Rect,
    scalingFactor: Float,
    color: Color,
    clickedOffset:Offset,
    radius:Float,
    gradientColors:List<Color>
) {
    translate(left = offset.x, top = offset.y) {
        scale(scalingFactor, bounds.topLeft) {
            drawPath(path, color = color)

            clipPath(path){
                drawCircle(
                    brush = Brush.radialGradient(gradientColors, clickedOffset, radius+0.1f),
                    radius = radius+0.1f,
                    center = clickedOffset
                )
            }
        }
    }
}

private fun getUnTransformedClickOffset(
    clickOffset:Offset,
    bounds:Rect,
    offset:Offset,
    scalingFactor: Float
) = if(clickOffset == Offset.Zero) bounds.center
else (clickOffset - offset) / scalingFactor

private fun getPath(pathString: String) = PathParser().parsePathString(pathString).toPath()