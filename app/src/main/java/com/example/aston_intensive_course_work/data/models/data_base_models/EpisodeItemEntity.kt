package com.example.aston_intensive_course_work.data.models.data_base_models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EpisodeItemEntity(
    @PrimaryKey
    val episodeId: Int,
    val episodeName: String,
    val episodeAirDate: String,
    val episodeNumber: String,
    val episodeCharacters: List<String>,
    val episodeUrl: String,
    val episodeCreated: String
)
