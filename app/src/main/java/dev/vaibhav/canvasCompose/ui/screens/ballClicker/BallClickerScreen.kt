package dev.vaibhav.canvasCompose.ui.screens.ballClicker

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.math.ceil
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

@Composable
fun BallClickerScreen(
    modifier: Modifier = Modifier,
    viewModel: BallClickerViewModel = viewModel(),
) {
    Box(modifier = modifier) {
        Column(modifier = Modifier.fillMaxSize()) {
            val timeLeft by viewModel.timeLeft.collectAsState()
            TopBar(viewModel = viewModel, modifier = Modifier.fillMaxWidth())
            BallScreen(
                viewModel = viewModel,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray)
                    .weight(1f),
                isEnabled = timeLeft != 0,
                spec = BallSpec(radius = 120f, color = Color.Green)
            )
        }
    }
}

@Composable
private fun TopBar(
    modifier: Modifier = Modifier,
    viewModel: BallClickerViewModel
) {
    val points by viewModel.points.collectAsState()
    val timeLeft by viewModel.timeLeft.collectAsState()
    Row(
        modifier = modifier.padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Points: $points", style = MaterialTheme.typography.titleMedium)
        Button(onClick = viewModel::onRestartButtonPressed) {
            Text(text = "Restart", style = MaterialTheme.typography.titleMedium)
        }
        Text(text = "Time left: ${timeLeft}s", style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
private fun BallScreen(
    spec: BallSpec,
    viewModel: BallClickerViewModel,
    isEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    val points by viewModel.points.collectAsState()
    BoxWithConstraints(modifier = modifier.padding(16.dp)) {
        val height = remember { constraints.maxHeight }
        val width = remember { constraints.maxWidth }

        var center by remember {
            mutableStateOf(getRandomCenter(spec.radius, height, width))
        }

        LaunchedEffect(key1 = points) {
            center = getRandomCenter(spec.radius, height, width)
        }

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(isEnabled) {
                    detectTapGestures {
                        if (!isEnabled) return@detectTapGestures
                        handleBallPress(
                            touchOffset = it,
                            center = center,
                            radius = spec.radius,
                            onBallPressed = viewModel::onBallPressed
                        )
                    }
                }
        ) {
            drawCircle(
                color = spec.color,
                center = center,
                radius = spec.radius
            )
        }
    }
}

private fun getRandomCenter(radius: Float, height: Int, width: Int): Offset {
    val rad = ceil(radius).toInt()
    return Offset(
        x = Random.nextInt(rad, width - rad).toFloat(),
        y = Random.nextInt(rad, height - rad).toFloat()
    )
}

private data class BallSpec(
    val radius: Float,
    val color: Color
)

private fun handleBallPress(
    touchOffset: Offset,
    center: Offset,
    radius: Float,
    onBallPressed: () -> Unit
) {
    val distance = touchOffset distFrom center
    if (distance <= radius) onBallPressed()
}

private infix fun Offset.distFrom(point: Offset): Float {
    return sqrt((x - point.x).pow(2) + (y - point.y).pow(2))
}
