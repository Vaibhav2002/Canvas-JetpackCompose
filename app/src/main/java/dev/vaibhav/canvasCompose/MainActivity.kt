package dev.vaibhav.canvasCompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import dev.vaibhav.canvasCompose.ui.screens.genderPicker.GenderPickerScreen
import dev.vaibhav.canvasCompose.ui.theme.CanvasComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CanvasComposeTheme {
                GenderPickerScreen(modifier = Modifier.fillMaxSize())
            }
        }
    }
}
