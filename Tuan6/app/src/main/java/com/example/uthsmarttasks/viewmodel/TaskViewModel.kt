package com.example.uthsmarttasks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uthsmarttasks.api.ApiService
import com.example.uthsmarttasks.data.TaskSummary
import com.example.uthsmarttasks.data.TaskApiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Định nghĩa các trạng thái của màn hình Home
sealed class TaskListUiState {
    object Loading : TaskListUiState()
    data class Success(val tasks: List<TaskSummary>) : TaskListUiState() // Màn 1
    object Empty : TaskListUiState() // Màn 2
    data class Error(val message: String) : TaskListUiState()
}

class TaskViewModel(private val apiService: ApiService) : ViewModel() {

    private val _uiState = MutableStateFlow<TaskListUiState>(TaskListUiState.Loading)
    val uiState: StateFlow<TaskListUiState> = _uiState

    init {
        fetchTasks()
    }

    // Sửa hàm fetchTasks()
    fun fetchTasks() {
        viewModelScope.launch {
            _uiState.value = TaskListUiState.Loading
            try {
                // Lấy về đối tượng TaskApiResponse
                val response = apiService.getAllTasks()

                // Lấy danh sách tasks từ BÊN TRONG đối tượng đó
                val tasks = response.data // <-- Sửa ở đây

                if (tasks.isEmpty()) {
                    _uiState.value = TaskListUiState.Empty
                } else {
                    _uiState.value = TaskListUiState.Success(tasks)
                }
            } catch (e: Exception) {
                _uiState.value = TaskListUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
    fun removeTaskFromList(taskId: String) {
        // Chỉ hành động nếu state hiện tại là Success
        val currentState = _uiState.value
        if (currentState is TaskListUiState.Success) {

            // Lấy danh sách task hiện tại
            val currentTasks = currentState.tasks

            // Tạo danh sách mới, lọc bỏ task có ID bị xóa
            val newTasks = currentTasks.filter { it.id != taskId }

            // Cập nhật UI
            if (newTasks.isEmpty()) {
                _uiState.value = TaskListUiState.Empty
            } else {
                _uiState.value = TaskListUiState.Success(newTasks)
            }
        }
    }
}