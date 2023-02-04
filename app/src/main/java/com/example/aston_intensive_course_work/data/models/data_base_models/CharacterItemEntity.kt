package com.example.aston_intensive_course_work.data.models.data_base_models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharacterItemEntity(
    @PrimaryKey
    val characterId: Int,
    val characterName: String,
    val characterStatus: String,
    val characterSpecies: String,
    val characterType: String,
    val characterGender: String,
    val characterOrigin: LocationSimpleItemEntity,
    val characterLocation: LocationSimpleItemEntity,
    val characterImage: String,
    val characterEpisode: List<String>,
    val characterUrl: String,
    val characterCreated: String
)

data class LocationSimpleItemEntity(
    val locationSimpleName: String,
    val locationSimpleUrl: String
)
