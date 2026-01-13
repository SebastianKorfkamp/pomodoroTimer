package com.example.pomodorotimer.Screens



import android.R.attr.id
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.util.TableInfo
import com.example.pomodorotimer.Composables.AddTodoDialog
import com.example.pomodorotimer.Composables.TodoItem
import com.example.pomodorotimer.Viewmodel.TodoViewModel
import com.example.pomodorotimer.Model.Todo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(todoViewModel: TodoViewModel = viewModel()) {

    // zugriff auf die liste aus dem viewmodel
    val todos by todoViewModel.todos
    var showDialog by remember { mutableStateOf(false) }

   Scaffold(
       floatingActionButton = {
           FloatingActionButton(onClick = { showDialog = true}) {
               Icon(Icons.Default.Add, contentDescription = "Add Todo")
           }
       }
   ) { padding ->
       Column(
           modifier = Modifier
               .fillMaxSize()
               .padding(padding)
               .padding(16.dp),
           horizontalAlignment = Alignment.CenterHorizontally
       ) {
           Text(text = "Meine todos", fontSize = 28.sp, style = MaterialTheme.typography.headlineMedium)

           Spacer(modifier = Modifier.height(16.dp))

           LazyColumn(modifier = Modifier.fillMaxSize()) {
               items(todos) {todo ->
                   TodoItem(
                       todo = todo,
                       onToggle = { todoViewModel.toggleTodoCompleted(todo.id)},
                       onDelete = {todoViewModel.deleteTodo(todo.id)}
                   )
               }
           }
       }
   }

    if (showDialog) {
        AddTodoDialog(
            onDismiss = { showDialog = false},
            onConfirm = { title, desc, poms ->
                todoViewModel.addTodo(title, poms, desc)
                showDialog = false
            }

        )
    }
}