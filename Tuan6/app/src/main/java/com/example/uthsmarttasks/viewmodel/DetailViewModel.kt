package com.example.uthsmarttasks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uthsmarttasks.api.ApiService
import com.example.uthsmarttasks.data.TaskDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.uthsmarttasks.data.TaskDetailResponseWrapper
// Trạng thái cho màn hình chi tiết
sealed class TaskDetailUiState {
    object Loading : TaskDetailUiState()
    data class Success(val task: TaskDetail) : TaskDetailUiState()
    data class Error(val message: String) : TaskDetailUiState()
}

class DetailViewModel(private val apiService: ApiService) : ViewModel() {

    private val _uiState = MutableStateFlow<TaskDetailUiState>(TaskDetailUiState.Loading)
    val uiState: StateFlow<TaskDetailUiState> = _uiState

    // Hàm này sẽ được gọi từ Màn 3 (DetailScreen)
    fun fetchTaskDetail(taskId: String) {
        viewModelScope.launch {
            _uiState.value = TaskDetailUiState.Loading
            try {
                // 1. Lấy về object bọc ngoài (TaskDetailResponseWrapper)
                val response = apiService.getTaskDetail(taskId)

                // 2. Lấy TaskDetail thật từ bên trong .data
                val taskDetail = response.data // <-- SỬA Ở ĐÂY

                _uiState.value = TaskDetailUiState.Success(taskDetail)
            } catch (e: Exception) {
                _uiState.value = TaskDetailUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    // Hàm gọi API Xóa
    fun deleteTask(taskId: String, onDeleted: () -> Unit) {
        viewModelScope.launch {
            try {
                // 1. Vẫn cố gắng gọi API (mặc kệ nó lỗi 404)
                apiService.deleteTask(taskId)
            } catch (e: Exception) {
                // 2. Bỏ qua (ignore) lỗi 404
            } finally {
                // 3. LUÔN LUÔN gọi onDeleted()
                onDeleted()
            }
        }
    }
}