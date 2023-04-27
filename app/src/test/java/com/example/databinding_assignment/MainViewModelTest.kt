package com.example.databinding_assignment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mainViewModel: MainViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        mainViewModel = MainViewModel()
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun onStartButtonClick_updateTimeLiveData() {
        runBlocking {
            mainViewModel.timeLiveData.observeForever{}
            mainViewModel.onStartButtonClicked()

            delay(5500)

            assertThat(mainViewModel.timeLiveData.value).isEqualTo("00:00:05")
        }
    }

    @Test
    fun onStartButtonClick_startsStopWatchJob() {
        runBlocking {
            mainViewModel.onStartButtonClicked()

            assertThat(mainViewModel.stopwatchJob?.isActive).isTrue()
        }
    }

    @Test
    fun onResetButtonClick_updateTimeLiveDataToDeFault() {
        runBlocking {
            mainViewModel.timeLiveData.observeForever{}
            mainViewModel.onStartButtonClicked()

            delay(5500)
            mainViewModel.onResetButtonClicked()

            assertThat(mainViewModel.timeLiveData.value).isEqualTo("00:00:00")
        }
    }

    @Test
    fun onResetButtonClick_cancelsStopWatchJob() {
        runBlocking {
            mainViewModel.onStartButtonClicked()
            delay(5500)

            mainViewModel.onResetButtonClicked()

            assertThat(mainViewModel.stopwatchJob?.isCancelled).isTrue()
        }
    }

    @Test
    fun onStopButtonClick_updateTimeLiveData() {
        runBlocking {
            mainViewModel.timeLiveData.observeForever{}
            mainViewModel.onStartButtonClicked()

            delay(5500)

            mainViewModel.onStopButtonClicked()

            assertThat(mainViewModel.timeLiveData.value).isEqualTo("00:00:05")
        }
    }

    @Test
    fun onStopButtonClick_startsStopWatchJob() {
        runBlocking {
            mainViewModel.onStartButtonClicked()

            delay(5500)

            mainViewModel.onResetButtonClicked()

            assertThat(mainViewModel.stopwatchJob?.isCancelled).isTrue()
        }
    }
}