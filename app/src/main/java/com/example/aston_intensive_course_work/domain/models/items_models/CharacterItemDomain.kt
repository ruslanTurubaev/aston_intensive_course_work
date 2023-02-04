package com.example.aston_intensive_course_work.domain.models.items_models

data class CharacterItemDomain(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: LocationSimpleItemDomain,
    val location: LocationSimpleItemDomain,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
)

data class LocationSimpleItemDomain(
    val name: String,
    val url: String
)