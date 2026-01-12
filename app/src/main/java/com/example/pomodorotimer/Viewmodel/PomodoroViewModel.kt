package com.example.pomodorotimer.Viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import com.example.pomodorotimer.Model.TimerMode
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import kotlinx.coroutines.job




class PomodoroViewModel(
     val settingsViewModel: SettingsViewModel = SettingsViewModel()
) : ViewModel() {


    // _timeLeft = private variable, die nur das Viewmodel ändern kann
    // timeleft = public variable, die die ui sehen kann (aber nciht ändern)
    // 1500 als startwert = 25 minutenx60 sekunden

    /*
    _TimeLeft holt die minuten jetzt aus dem settingsviewmodel, multipliziert diese mit 60 um sekunden zu bekommen
    und setzt das ganze als startwert
     */

    private val _timeLeft = mutableStateOf(settingsViewModel.workDuration.value * 60)
    val timeLeft: State<Int> = _timeLeft

    private val _isRunning = mutableStateOf(false)
    val isRunning: State<Boolean> = _isRunning

    private val _currentmode = mutableStateOf(TimerMode.WORK)
    val currentMode: State<TimerMode> = _currentmode


    // die variable brauchen wir um den timer zu stoppen
    private var timerJob: Job? = null

    private val _completedPomodoros = mutableStateOf(0)
    val completedPomodoros: State<Int> = _completedPomodoros





    fun StartTimer() {


        if (_isRunning.value) return // Verhindert den Doppelstart

        _isRunning.value = true

        timerJob = viewModelScope.launch {
             while (_isRunning.value && _timeLeft.value > 0) {
                 delay(1000) // warte 1 sekunde
                 _timeLeft.value -= 1 // ziehe 1 sekunde ab
             }

            if (_timeLeft.value == 0) {
                _isRunning.value = false
                switchToNextMode()

            }

        }


    }


    fun pauseTimer() {
        _isRunning.value = false
        timerJob?.cancel()
    }

    fun resetTimer() {
        _isRunning.value = false
        timerJob?.cancel()

        val duration = when (_currentmode.value) {
            TimerMode.WORK ->  settingsViewModel.workDuration.value
            TimerMode.SHORT_BREAK -> settingsViewModel.shortBreakDuration.value
            TimerMode.LONG_BREAK -> settingsViewModel.longBreakDuration.value
        }

        _timeLeft.value = duration * 60

    }

    fun switchToNextMode() {
        _currentmode.value = when (_currentmode.value) {
           TimerMode.WORK ->  {
               _completedPomodoros.value += 1

               if (_completedPomodoros.value >= 4) {
                   _completedPomodoros.value = 0
                   TimerMode.LONG_BREAK

               } else   {
                   TimerMode.SHORT_BREAK

               }
           }
            TimerMode.SHORT_BREAK -> TimerMode.WORK
            TimerMode.LONG_BREAK -> TimerMode.WORK
        }
        resetTimer()
    }

}