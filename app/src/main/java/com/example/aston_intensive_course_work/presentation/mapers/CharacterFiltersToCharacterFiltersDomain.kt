package com.example.aston_intensive_course_work.presentation.mapers

import com.example.aston_intensive_course_work.presentation.models.filter_models.CharacterFilters
import com.example.aston_intensive_course_work.domain.models.filters_models.CharacterFiltersDomain

object CharacterFiltersToCharacterFiltersDomain {
    fun map(characterFilters: CharacterFilters): CharacterFiltersDomain = CharacterFiltersDomain(
        name = characterFilters.name,
        status = characterFilters.status,
        species = characterFilters.species,
        type = characterFilters.type,
        gender = characterFilters.gender
    )
}