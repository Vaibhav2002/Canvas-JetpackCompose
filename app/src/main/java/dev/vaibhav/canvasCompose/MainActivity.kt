package dev.vaibhav.canvasCompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.vaibhav.canvasCompose.ui.screens.clock.Clock
import dev.vaibhav.canvasCompose.ui.screens.clock.ClockScreen
import dev.vaibhav.canvasCompose.ui.theme.CanvasComposeTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CanvasComposeTheme {
                ClockScreen(modifier = Modifier.fillMaxSize())
            }
        }
    }
}
