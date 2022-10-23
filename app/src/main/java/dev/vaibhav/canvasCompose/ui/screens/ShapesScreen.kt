package dev.vaibhav.canvasCompose.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.vaibhav.canvasCompose.ui.components.arc
import dev.vaibhav.canvasCompose.ui.components.circle
import dev.vaibhav.canvasCompose.ui.components.rectangle
import dev.vaibhav.canvasCompose.ui.components.square

@Composable
fun ShapesScreen(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.padding(16.dp)) {
        Canvas(
            modifier = Modifier
                .size(400.dp, 400.dp)
                .background(Color.LightGray)
        ) {
            rectangle(offset = Offset(100f, 50f), size = Size(250f, 150f))
            square(offset = Offset(500f, 100f), size = Size(200f, 200f))
            circle(center, 200f)
            arc(
                color = Color.Green,
                offset = Offset(50f, 220.dp.toPx()),
                angle = 170f,
                strokeWidth = 16.dp.toPx(),
                size = Size(400f, 400f)
            )
        }
    }
}
