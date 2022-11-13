package dev.vaibhav.canvasCompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.vaibhav.canvasCompose.ui.screens.androidLogo.AndroidLogoScreen
import dev.vaibhav.canvasCompose.ui.screens.arrowScreen.ArrowScreen
import dev.vaibhav.canvasCompose.ui.screens.ballClicker.BallClickerScreen
import dev.vaibhav.canvasCompose.ui.screens.circularProgress.CircularProgressBar
import dev.vaibhav.canvasCompose.ui.screens.circularProgress.CircularProgressScreen
import dev.vaibhav.canvasCompose.ui.screens.clock.Clock
import dev.vaibhav.canvasCompose.ui.screens.clock.ClockScreen
import dev.vaibhav.canvasCompose.ui.screens.path.PathScreen
import dev.vaibhav.canvasCompose.ui.screens.shapes.ShapesScreen
import dev.vaibhav.canvasCompose.ui.screens.transformations.TransformationsScreen
import dev.vaibhav.canvasCompose.ui.theme.CanvasComposeTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CanvasComposeTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TransformationsScreen(modifier = Modifier.fillMaxSize().padding(16.dp))
                }
            }
        }
    }
}
