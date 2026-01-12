package com.example.pomodorotimer.Screens



import androidx.compose.foundation.layout.*
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pomodorotimer.Composables.SettingSlider
import com.example.pomodorotimer.Viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        Text(
            text = "Einstellungen",
            fontSize = 32.sp
        )


        SettingSlider(
            title = "Arbeitszeit",
            value = viewModel.workDuration.value,
            valueRange = 1..60,
            onValueChange = { viewModel.updateWorkDuration(it) }
        )


        SettingSlider(
            title = "Kurze Pause",
            value = viewModel.shortBreakDuration.value,
            valueRange = 1..15,
            onValueChange = { viewModel.updateShortBreakDuration(it) }
        )


        SettingSlider(
            title = "Lange Pause",
            value = viewModel.longBreakDuration.value,
            valueRange = 5..30,
            onValueChange = { viewModel.updateLongBreakDuration(it) }
        )
    }
}
