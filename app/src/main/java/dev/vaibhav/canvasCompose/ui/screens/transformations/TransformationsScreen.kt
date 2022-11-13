package dev.vaibhav.canvasCompose.ui.screens.transformations

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private sealed interface TransformationType {
    object Translate : TransformationType
    object Rotation : TransformationType
    object Scale : TransformationType
    object All:TransformationType
}

@Composable
fun TransformationsScreen(
    modifier: Modifier = Modifier
) {
    var transformationType by remember {
        mutableStateOf(TransformationType.Translate as TransformationType)
    }
    val duration = 3_000
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        CanvasContent(
            duration,
            type = transformationType,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TransitionButton(text = "Translation") {
                transformationType = TransformationType.Translate
            }
            TransitionButton(text = "Rotation") {
                transformationType = TransformationType.Rotation
            }
            TransitionButton(text = "Scaling") {
                transformationType = TransformationType.Scale
            }
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
        contentAlignment = Alignment.TopCenter){
            TransitionButton(text = "All Transitions") {
                transformationType = TransformationType.All
            }
        }
    }
}

@Composable
fun TransitionButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    ElevatedButton(onClick = onClick, modifier = modifier) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun CanvasContent(duration:Int, type: TransformationType, modifier: Modifier) {
    AnimatedContent(
        targetState = type,
        modifier = modifier
    ) {
        when (it) {
            TransformationType.Scale -> ScalingCanvas(duration, Modifier.fillMaxSize())
            TransformationType.Rotation -> RotationCanvas(duration, Modifier.fillMaxSize())
            TransformationType.Translate -> TranslateCanvas(duration, Modifier.fillMaxSize())
            TransformationType.All -> AllTransformationCanvas(
                translateDuration = 5_000,
                rotateDuration = 2_500,
                scaleDuration = 1_500
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun TransformationScreenPrev() {
    TransformationsScreen()
}