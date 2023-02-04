package com.example.aston_intensive_course_work.domain.use_cases

import androidx.paging.PagingData
import com.example.aston_intensive_course_work.domain.interfaces.EpisodeRepositoryInterface
import com.example.aston_intensive_course_work.domain.models.items_models.EpisodeItemDomain
import com.example.aston_intensive_course_work.domain.utils.getIdListFromUrlList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllEpisodesByIdsUseCase @Inject constructor(private val repository: EpisodeRepositoryInterface) {

    fun execute(itemsArray: List<String>): Flow<PagingData<EpisodeItemDomain>> {
        val itemsId = getIdListFromUrlList(urlList = itemsArray)

        return repository.getAllEpisodesById(itemsId = itemsId)
    }
}