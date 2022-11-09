package dev.vaibhav.canvasCompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.vaibhav.canvasCompose.ui.screens.clock.Clock
import dev.vaibhav.canvasCompose.ui.theme.CanvasComposeTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CanvasComposeTheme {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Clock()
                }

            }
        }
    }

    @Composable
    fun Clock(
        modifier: Modifier = Modifier
    ) {
        val millis by remember { mutableStateOf(System.currentTimeMillis()) }
        var seconds by remember { mutableStateOf((millis / 100f) % 60) }
        var minutes by remember { mutableStateOf((millis / 1000f / 60f) % 60) }
        var hours by remember { mutableStateOf(millis / 1000f / 60 / 60) }

        LaunchedEffect(key1 = seconds){
            delay(1000L)
            minutes+=1f / 60
            hours+= 1f / (60*60*12)
            seconds++
        }
        Clock(
            hours = hours,
            minutes = minutes,
            seconds = seconds,
            size = 200.dp,
            modifier = modifier
        )

    }
}
