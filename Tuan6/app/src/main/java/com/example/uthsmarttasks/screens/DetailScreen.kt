package com.example.uthsmarttasks.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons // <-- THÊM IMPORT NÀY
import androidx.compose.material.icons.automirrored.filled.ArrowBack // <-- THÊM IMPORT NÀY
import androidx.compose.material.icons.filled.Delete // <-- THÊM IMPORT NÀY
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.uthsmarttasks.data.TaskDetail
import com.example.uthsmarttasks.viewmodel.DetailViewModel
import com.example.uthsmarttasks.viewmodel.TaskDetailUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    taskId: String,
    viewModel: DetailViewModel,
    onBackClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = taskId) {
        viewModel.fetchTaskDetail(taskId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail") },

                // === NÚT QUAY LẠI (BACK) ===
                navigationIcon = {
                    IconButton(onClick = onBackClick) { // <-- Gọi lambda onBackClick
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Quay lại"
                        )
                    }
                },

                // === NÚT XÓA (DELETE) ===
                actions = {
                    IconButton(onClick = {
                        viewModel.deleteTask(taskId, onDeleted = onDeleteClick)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Xóa"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (val state = uiState) {
                is TaskDetailUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is TaskDetailUiState.Success -> {
                    TaskDetailContent(task = state.task)
                }
                is TaskDetailUiState.Error -> {
                    Text(
                        text = "Lỗi: ${state.message}",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

// Composable hiển thị nội dung chi tiết của Màn 3
@Composable
fun TaskDetailContent(task: TaskDetail) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = task.title ?: "Không có tiêu đề",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Category: ${task.category ?: "N/A"}")
        Text(text = "Status: ${task.status ?: "N/A"}")
        Text(text = "Priority: ${task.priority ?: "N/A"}")

        // ... Hiển thị Subtasks và Attachments (nhớ xử lý null) ...
        task.subtasks?.let { subtasks ->
            Spacer(modifier = Modifier.height(16.dp))
            Text("Subtasks:", style = MaterialTheme.typography.titleMedium)
            subtasks.forEach { subtask ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = subtask.isCompleted, onCheckedChange = null)
                    Text(text = subtask.title ?: "N/A")
                }
            }
        }
    }
}