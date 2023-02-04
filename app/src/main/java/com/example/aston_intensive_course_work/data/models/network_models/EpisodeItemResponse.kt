package com.example.aston_intensive_course_work.data.models.network_models

import com.google.gson.annotations.SerializedName

data class EpisodeItemResponse(
    val id: Int,
    val name: String,
    @SerializedName("air_date")
    val airDate: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
)
