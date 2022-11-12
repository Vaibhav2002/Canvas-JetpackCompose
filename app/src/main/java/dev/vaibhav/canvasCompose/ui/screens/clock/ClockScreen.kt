package dev.vaibhav.canvasCompose.ui.screens.clock

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun ClockScreen(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        val millis by remember { mutableStateOf(System.currentTimeMillis()) }
        var seconds by remember { mutableStateOf((millis / 100f) % 60) }
        var minutes by remember { mutableStateOf((millis / 1000f / 60f) % 60) }
        var hours by remember { mutableStateOf(millis / 1000f / 60 / 60) }

        LaunchedEffect(key1 = seconds) {
            delay(1000L)
            minutes += 1f / 60
            hours += 1f / (60 * 60 * 12)
            seconds++
        }
        Clock(
            hours = hours,
            minutes = minutes,
            seconds = seconds,
            size = 200.dp
        )
    }
}