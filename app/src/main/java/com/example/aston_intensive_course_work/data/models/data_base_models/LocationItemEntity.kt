package com.example.aston_intensive_course_work.data.models.data_base_models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocationItemEntity(
    @PrimaryKey
    val locationId: Int,
    val locationName: String,
    val locationType: String,
    val locationDimension: String,
    val locationResidents: List<String>,
    val locationUrl: String,
    val locationCreated: String
)
