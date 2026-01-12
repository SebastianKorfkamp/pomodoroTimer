package com.example.pomodorotimer.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pomodorotimer.Viewmodel.PomodoroViewModel
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.LaunchedEffect
import com.example.pomodorotimer.Model.TimerMode


@Composable
fun PomodoroScreen(
    viewModel: PomodoroViewModel = viewModel()
) {
    // Liest den State aus dem ViewModel
    val timeLeft by viewModel.timeLeft
    val isRunning by viewModel.isRunning
    val currentMode by viewModel.currentMode
    val completedPomodoros by viewModel.completedPomodoros

    LaunchedEffect(currentMode, viewModel.settingsViewModel.workDuration.value,
        viewModel.settingsViewModel.shortBreakDuration.value,
        viewModel.settingsViewModel.longBreakDuration.value) {
        if (!isRunning) {
            viewModel.resetTimer()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = when (currentMode) {
                TimerMode.WORK -> "Arbeitszeit"
                TimerMode.SHORT_BREAK -> "Kurze Pause"
                TimerMode.LONG_BREAK -> "Lange Pause"
            },
            fontSize = 24.sp
        )


        Text(
            text = "Pomodoro: $completedPomodoros / 4",
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = formatTime(timeLeft),
            fontSize = 72.sp
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = {
                if (isRunning) {
                    viewModel.pauseTimer()
                } else {
                    viewModel.StartTimer()
                }
            }) {
                Text(text = if (isRunning) "Pause" else "Start")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { viewModel.resetTimer() }) {
                Text(text = "Reset")
            }

        }
    }
}

// Formatiert Sekunden zu MM:SS Format
private fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}