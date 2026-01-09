package com.example.pomodorotimer.Viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import kotlinx.coroutines.job




class PomodoroViewModel : ViewModel() {


    // _timeLeft = private variable, die nur das Viewmodel ändern kann
    // timeleft = public variable, die die ui sehen kann (aber nciht ändern)
    // 1500 als startwert = 25 minutenx60 sekunden

    private val _timeLeft = mutableStateOf(1500) // 25 minuten
    val timeLeft: State<Int> = _timeLeft

    private val _isRunning = mutableStateOf(false)
    val isRunning: State<Boolean> = _isRunning


    // die variable brauchen wir um den timer zu stoppen
    private var timerJob: Job? = null



    // Funktionen um den Timer zu steuern
    // StartTimer - setzt is running auf true
    // pauseTimer - setzt isRunning auf false
    // resetTimer - Stoppt den timer und setzt die zeit zurück auf 25 minuten


    /*
    StartTimer() funktion

    ViewmModelScope - startet eine Coroutine
    die while schleife läuift solange der timer noch aktiv ist und zeit übrig ist
    Delay(1000) wartet 1000 Millisekunden (1sekunde)
    _timeLeft.value -=1 - zieht 1 sekunde ab

     */

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

            }

        }


    }


    fun pauseTimer() {
        _isRunning.value = false
    }

    fun resesTimer() {
        _isRunning.value = false
        _timeLeft.value = 1500
    }

}