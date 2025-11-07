package com.example.uthsmarttasks.api


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    // Thay thế bằng Base URL của API (từ file ảnh của bạn)
    private const val BASE_URL = "https://amock.io/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Đây là hàm quan trọng để lấy ApiService
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}