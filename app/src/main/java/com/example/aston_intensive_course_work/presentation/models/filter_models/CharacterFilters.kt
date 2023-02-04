package com.example.aston_intensive_course_work.presentation.models.filter_models

import com.example.aston_intensive_course_work.presentation.interfaces.Filter

data class CharacterFilters(
    val name: String,
    var status: String,
    val species: String,
    val type: String,
    var gender: String
) : Filter
