package com.example.aston_intensive_course_work.data.models.network_models

data class ApiResponse<T>(
    val info: InfoResponse,
    val results: List<T>
)