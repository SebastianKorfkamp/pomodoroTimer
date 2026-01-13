package com.example.pomodorotimer.Model


import java.util.UUID

data class Todo(
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val description: String = "",
    val estimatedPomodoros: Int,  // geschätzte Anzahl
    val completedPomodoros: Int = 0,  // tatsächlich erledigte
    val isCompleted: Boolean = false  // ist das Todo fertig?
)