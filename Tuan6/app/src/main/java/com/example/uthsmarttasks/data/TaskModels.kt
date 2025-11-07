package com.example.uthsmarttasks.data

// Dữ liệu tóm tắt cho màn hình danh sách
data class TaskSummary(
    val id: String,
    val title: String?,
    val status: String?,
    val dueDate: String?
    // Thêm các trường khác nếu API list trả về
)

// Dữ liệu chi tiết cho màn hình 3
data class TaskDetail(
    val id: Int,
    val title: String?,
    val description: String?,
    val category: String?,
    val status: String?,
    val priority: String?,
    val subtasks: List<Subtask>?,
    val attachments: List<Attachment>?

)
data class TaskApiResponse(
    val data: List<TaskSummary>,
    val status: String

)
data class Subtask(
    val id: Int,
    val title: String?, // <-- JSON dùng "title", không phải "name"
    val isCompleted: Boolean
)

data class Attachment(
    val id: Int,
    val fileName: String?,
    val fileUrl: String? // <-- Thêm trường này
)

data class TaskDetailResponseWrapper(
    val isSuccess: Boolean,
    val message: String,
    val data: TaskDetail
)
data class GenericApiResponse(
    val isSuccess: Boolean,
    val message: String
)