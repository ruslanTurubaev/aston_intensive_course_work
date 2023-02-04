package com.example.aston_intensive_course_work.presentation.models.items_models

import com.example.aston_intensive_course_work.presentation.interfaces.Item

data class LocationItem(
    override val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    val url: String,
    val created: String
) : Item
