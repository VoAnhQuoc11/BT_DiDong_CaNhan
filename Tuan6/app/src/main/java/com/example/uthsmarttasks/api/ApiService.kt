package com.example.uthsmarttasks.api
import com.example.uthsmarttasks.data.TaskApiResponse
import com.example.uthsmarttasks.data.TaskDetail
import com.example.uthsmarttasks.data.TaskDetailResponseWrapper
import com.example.uthsmarttasks.data.TaskSummary
import com.example.uthsmarttasks.data.GenericApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.DELETE
import retrofit2.Response

interface ApiService {

    // API cho Màn 1 & 2: Lấy tất cả tasks
// File: api/ApiService.kt
    @GET("api/researchUTH/tasks")
    suspend fun getAllTasks(): TaskApiResponse// <-- Vấn đề ở đây

    // API cho Màn 3: Lấy chi tiết task
    @GET("api/researchUTH/task/{id}") // <-- Đảm bảo đã sửa thành "task"
    suspend fun getTaskDetail(@Path("id") taskId: String): TaskDetailResponseWrapper

    // API cho Màn 3: Xóa task
    @DELETE("api/researchUTH/task/{id}")
    suspend fun deleteTask(@Path("id") taskId: String): GenericApiResponse

}