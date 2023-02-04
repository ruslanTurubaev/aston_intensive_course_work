package com.example.aston_intensive_course_work.domain.models.items_models

data class LocationItemDomain(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    val url: String,
    val created: String
)
