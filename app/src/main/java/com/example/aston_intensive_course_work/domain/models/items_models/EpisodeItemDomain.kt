package com.example.aston_intensive_course_work.domain.models.items_models

data class EpisodeItemDomain(
    val id: Int,
    val name: String,
    val airDate: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
)
