package com.example.uthsmarttasks.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.uthsmarttasks.data.TaskSummary
import com.example.uthsmarttasks.viewmodel.TaskListUiState
import com.example.uthsmarttasks.viewmodel.TaskViewModel

@Composable
fun HomeScreen(
    viewModel: TaskViewModel, // Nhận ViewModel
    onTaskClick: (String) -> Unit, // Lambda để điều hướng
    onLogoutClick: () -> Unit
) {
    // Lắng nghe trạng thái từ ViewModel
    val uiState by viewModel.uiState.collectAsState()

    // Sử dụng when để quyết định hiển thị UI nào
    when (val state = uiState) {
        is TaskListUiState.Loading -> {
            // Hiển thị vòng xoay loading
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is TaskListUiState.Empty -> {
            // **Đây là Màn 2 (EmptyView)**
            EmptyTaskScreen()
        }
        is TaskListUiState.Success -> {
            // **Đây là Màn 1 (List)**
            TaskListScreen(tasks = state.tasks, onTaskClick = onTaskClick)
        }
        is TaskListUiState.Error -> {
            // Hiển thị lỗi
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Lỗi: ${state.message}")
            }
        }
    }
}

// Composable cho Màn 1
@Composable
fun TaskListScreen(tasks: List<TaskSummary>, onTaskClick: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(tasks) { task ->
            TaskItem(task = task, onClick = { onTaskClick(task.id) })
        }
    }
}

// Composable cho Màn 2
@Composable
fun EmptyTaskScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Icon(painter = ..., contentDescription = null) // Thêm icon của bạn
            Text(text = "No Tasks Yet!", style = MaterialTheme.typography.headlineSmall)
            Text(text = "Stay productive - add something to do")
        }
    }
}

// Composable cho một item trong danh sách
@Composable
fun TaskItem(task: TaskSummary, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick), // **Khi nhấn vào sẽ gọi lambda**
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = task.title ?: "Không có tiêu đề", // <-- Xử lý null
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Status: ${task.status ?: "N/A"}", // <-- Xử lý null
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Due: ${task.dueDate ?: "N/A"}", // <-- Xử lý null
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}