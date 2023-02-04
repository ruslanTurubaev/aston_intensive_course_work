package com.example.aston_intensive_course_work.data.models.network_models

data class LocationItemResponse(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    val url: String,
    val created: String
)
