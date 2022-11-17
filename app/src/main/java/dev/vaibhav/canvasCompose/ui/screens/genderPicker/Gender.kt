package dev.vaibhav.canvasCompose.ui.screens.genderPicker

sealed class Gender(val text:String) {
    object Male : Gender("Male")
    object Female : Gender("Female")
}