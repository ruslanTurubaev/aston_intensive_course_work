package com.example.aston_intensive_course_work.presentation.mapers

import com.example.aston_intensive_course_work.presentation.models.items_models.CharacterItem
import com.example.aston_intensive_course_work.domain.models.items_models.CharacterItemDomain

object CharacterDomainToCharacterItem {
    fun map(characterItemDomain: CharacterItemDomain): CharacterItem = CharacterItem(
        id = characterItemDomain.id,
        name = characterItemDomain.name,
        status = characterItemDomain.status,
        species = characterItemDomain.species,
        type = characterItemDomain.type,
        gender = characterItemDomain.gender,
        origin = LocationSimpleDomainToLocationSimpleItem.map(characterItemDomain.origin),
        location = LocationSimpleDomainToLocationSimpleItem.map(characterItemDomain.location),
        image = characterItemDomain.image,
        episode = characterItemDomain.episode,
        url = characterItemDomain.url,
        created = characterItemDomain.created
    )
}