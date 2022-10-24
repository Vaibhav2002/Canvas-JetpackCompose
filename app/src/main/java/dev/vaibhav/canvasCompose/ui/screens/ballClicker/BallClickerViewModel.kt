package dev.vaibhav.canvasCompose.ui.screens.ballClicker

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BallClickerViewModel : ViewModel() {

    companion object {
        private const val TIME = 30 * 1000L
        private const val TIME_INTERVAL = 1000L
    }

    val timeLeft = MutableStateFlow(30)
    val points = MutableStateFlow(0)

    private val timer = object : CountDownTimer(TIME, TIME_INTERVAL) {
        override fun onTick(p0: Long) {
            timeLeft.update { p0.div(1000).toInt() }
        }

        override fun onFinish() {
            timeLeft.update { 0 }
        }
    }

    init {
        reStart()
    }

    private fun reStart() = viewModelScope.launch {
        points.emit(0)
        timer.cancel()
        timer.start()
    }

    fun onBallPressed() {
        points.update { it + 1 }
    }

    fun onRestartButtonPressed() = reStart()
}
