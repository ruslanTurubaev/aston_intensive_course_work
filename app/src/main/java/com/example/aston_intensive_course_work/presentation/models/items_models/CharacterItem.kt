package com.example.aston_intensive_course_work.presentation.models.items_models

import com.example.aston_intensive_course_work.presentation.interfaces.Item

data class CharacterItem(
    override val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: LocationSimpleItem,
    val location: LocationSimpleItem,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
) : Item

data class LocationSimpleItem(
    val name: String,
    val url: String
)