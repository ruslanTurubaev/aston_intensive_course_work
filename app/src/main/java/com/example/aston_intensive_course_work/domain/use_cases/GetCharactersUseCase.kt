package com.example.aston_intensive_course_work.domain.use_cases

import androidx.paging.PagingData
import com.example.aston_intensive_course_work.domain.interfaces.CharacterRepositoryInterface
import com.example.aston_intensive_course_work.domain.models.items_models.CharacterItemDomain
import com.example.aston_intensive_course_work.domain.models.filters_models.CharacterFiltersDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(private val repository: CharacterRepositoryInterface) {

    fun execute(characterFilters: CharacterFiltersDomain): Flow<PagingData<CharacterItemDomain>> {
        val name = characterFilters.name
        val status = characterFilters.status
        val species = characterFilters.species
        val type = characterFilters.type
        val gender = characterFilters.gender

        return repository.getCharacters(
            name = name,
            status = status,
            species = species,
            type = type,
            gender = gender
        )
    }
}