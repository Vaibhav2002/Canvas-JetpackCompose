package dev.vaibhav.canvasCompose.ui.screens.genderPicker

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun GenderPickerScreen(
    modifier: Modifier = Modifier
) {
    GenderPicker(modifier = modifier){
        Log.d("Click", "Clicked on ${it.text}")
    }
}