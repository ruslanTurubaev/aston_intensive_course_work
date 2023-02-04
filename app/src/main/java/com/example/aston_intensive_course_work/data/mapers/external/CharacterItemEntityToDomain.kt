package com.example.aston_intensive_course_work.data.mapers.external

import com.example.aston_intensive_course_work.data.models.data_base_models.CharacterItemEntity
import com.example.aston_intensive_course_work.domain.models.items_models.CharacterItemDomain

object CharacterItemEntityToDomain {
    fun map(characterItemEntity: CharacterItemEntity): CharacterItemDomain = CharacterItemDomain(
        id = characterItemEntity.characterId,
        name = characterItemEntity.characterName,
        status = characterItemEntity.characterStatus,
        species = characterItemEntity.characterSpecies,
        type = characterItemEntity.characterType,
        gender = characterItemEntity.characterGender,
        origin = LocationSimpleItemEntityToDomain.map(characterItemEntity.characterOrigin),
        location = LocationSimpleItemEntityToDomain.map(characterItemEntity.characterLocation),
        image = characterItemEntity.characterImage,
        episode = characterItemEntity.characterEpisode,
        url = characterItemEntity.characterUrl,
        created = characterItemEntity.characterCreated
    )
}