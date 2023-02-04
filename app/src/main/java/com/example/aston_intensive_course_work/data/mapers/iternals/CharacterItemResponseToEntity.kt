package com.example.aston_intensive_course_work.data.mapers.iternals

import com.example.aston_intensive_course_work.data.models.data_base_models.CharacterItemEntity
import com.example.aston_intensive_course_work.data.models.network_models.CharacterItemResponse

object CharacterItemResponseToEntity {
    fun map(characterItemResponse: CharacterItemResponse): CharacterItemEntity =
        CharacterItemEntity(
            characterId = characterItemResponse.id,
            characterName = characterItemResponse.name,
            characterStatus = characterItemResponse.status,
            characterSpecies = characterItemResponse.species,
            characterType = characterItemResponse.type,
            characterGender = characterItemResponse.gender,
            characterOrigin = LocationSimpleItemResponseToEntity.map(characterItemResponse.origin),
            characterLocation = LocationSimpleItemResponseToEntity.map(characterItemResponse.location),
            characterImage = characterItemResponse.image,
            characterEpisode = characterItemResponse.episode,
            characterUrl = characterItemResponse.url,
            characterCreated = characterItemResponse.created
        )
}