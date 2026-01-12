package com.example.pomodorotimer.Viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {

    private val _workDuration = mutableStateOf(1)
    val workDuration: State<Int> = _workDuration

    private val _shortBreakDuration = mutableStateOf(5)
    val shortBreakDuration: State<Int> = _shortBreakDuration

    private val _longBreakDuration = mutableStateOf(15)
    val longBreakDuration: State<Int> = _longBreakDuration


fun updateWorkDuration(minutes: Int) {
    _workDuration.value = minutes
}

    fun updateShortBreakDuration(minutes: Int) {
        _shortBreakDuration.value = minutes
    }

    fun updateLongBreakDuration(minutes: Int) {
        _longBreakDuration.value = minutes
    }

}