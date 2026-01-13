package com.example.pomodorotimer.Viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.pomodorotimer.Model.Todo
import java.util.UUID

class TodoViewModel : ViewModel() {

    private val _todos = mutableStateOf<List<Todo>>(emptyList())
    val todos: State<List<Todo>> = _todos



    fun addTodo(title: String, estimatedPomodoros: Int, description: String = "") {
        val newTodo = Todo(
            title = title,
            estimatedPomodoros = estimatedPomodoros,
            description = description
        )
        _todos.value = _todos.value + newTodo
    }

    fun deleteTodo(todoId: UUID) {
        _todos.value = _todos.value.filter { it.id != todoId }
    }

    fun toggleTodoCompleted(todoId: UUID) {
        _todos.value = _todos.value.map { todo ->
            if (todo.id == todoId) {
                todo.copy(isCompleted = !todo.isCompleted)
            } else  {
                todo
            }
        }
    }
}