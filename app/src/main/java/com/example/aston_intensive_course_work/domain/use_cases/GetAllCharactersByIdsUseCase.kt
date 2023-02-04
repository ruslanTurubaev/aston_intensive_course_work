package com.example.aston_intensive_course_work.domain.use_cases

import androidx.paging.PagingData
import com.example.aston_intensive_course_work.domain.interfaces.CharacterRepositoryInterface
import com.example.aston_intensive_course_work.domain.models.items_models.CharacterItemDomain
import com.example.aston_intensive_course_work.domain.utils.getIdListFromUrlList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCharactersByIdsUseCase @Inject constructor(private val repository: CharacterRepositoryInterface) {

    fun execute(itemsArray: List<String>): Flow<PagingData<CharacterItemDomain>> {
        val itemsId = getIdListFromUrlList(urlList = itemsArray)

        return repository.getAllCharacterById(itemsId = itemsId)
    }
}