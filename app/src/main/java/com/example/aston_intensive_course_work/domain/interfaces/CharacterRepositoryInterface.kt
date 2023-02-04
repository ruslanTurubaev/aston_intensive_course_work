package com.example.aston_intensive_course_work.domain.interfaces

import androidx.paging.PagingData
import com.example.aston_intensive_course_work.domain.models.items_models.CharacterItemDomain
import kotlinx.coroutines.flow.Flow

interface CharacterRepositoryInterface {

    fun getCharacters(
        name: String,
        status: String,
        species: String,
        type: String,
        gender: String
    ): Flow<PagingData<CharacterItemDomain>>

    fun getAllCharacterById(itemsId: List<Int>): Flow<PagingData<CharacterItemDomain>>
}