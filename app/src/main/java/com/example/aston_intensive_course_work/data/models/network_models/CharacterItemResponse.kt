package com.example.aston_intensive_course_work.data.models.network_models

data class CharacterItemResponse(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: LocationSimpleItemResponse,
    val location: LocationSimpleItemResponse,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
)

data class LocationSimpleItemResponse(
    val name: String,
    val url: String
)