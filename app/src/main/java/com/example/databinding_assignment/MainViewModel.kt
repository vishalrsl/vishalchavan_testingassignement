package com.example.databinding_assignment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MainViewModel: ViewModel() {

    private var currentTimeInSeconds:Long = 0
    internal var stopwatchJob: Job? = null

    val timeLiveData = MutableLiveData(INITIAL_TIME)

    // Action to be performed when start button is clicked.
    fun onStartButtonClicked() {
        startStopwatchJob()
    }

    // Action to be performed when reset button is clicked.
    fun onResetButtonClicked() {
        cancelStopwatchJob()
        setInitialStopwatch()
        currentTimeInSeconds = 0
    }

    // Action to be performed when stop button is clicked.
    fun onStopButtonClicked() {
        cancelStopwatchJob()
    }

    private fun startStopwatchJob() {
        cancelStopwatchJob()
        stopwatchJob =  viewModelScope.launch {
            while (true) {
                delay(SECOND_DELAY)
                currentTimeInSeconds += 1
                updateStopwatchTime()
            }
        }
    }

    private fun cancelStopwatchJob() {
        stopwatchJob?.cancel()
    }

    private fun updateStopwatchTime() {
        var milliseconds = currentTimeInSeconds * 1000
        val hours = TimeUnit.MILLISECONDS.toHours(milliseconds)
        milliseconds -= TimeUnit.HOURS.toMillis(hours)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
        milliseconds -= TimeUnit.MINUTES.toMillis(minutes)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds)

        val currentTimeString = "${if (hours < 10) "0" else ""}$hours:" +
                "${if (minutes < 10) "0" else ""}$minutes:" +
                "${if (seconds < 10) "0" else ""}$seconds"
        timeLiveData.value = currentTimeString
    }

    private fun setInitialStopwatch() {
        timeLiveData.value = INITIAL_TIME
    }

    private companion object {
        const val INITIAL_TIME = "00:00:00"
        const val SECOND_DELAY = 1000L
    }
}