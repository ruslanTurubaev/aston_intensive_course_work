package com.example.aston_intensive_course_work.presentation.models.filter_models

import com.example.aston_intensive_course_work.presentation.interfaces.Filter

data class EpisodeFilter(
    val name: String,
    val episode: String
) : Filter
