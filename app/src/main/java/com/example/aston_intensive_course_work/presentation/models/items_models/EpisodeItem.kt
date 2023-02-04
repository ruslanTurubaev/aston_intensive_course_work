package com.example.aston_intensive_course_work.presentation.models.items_models

import com.example.aston_intensive_course_work.presentation.interfaces.Item

data class EpisodeItem(
    override val id: Int,
    val name: String,
    val airDate: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
) : Item
