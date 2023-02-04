package com.example.aston_intensive_course_work.domain.interfaces

import androidx.paging.PagingData
import com.example.aston_intensive_course_work.domain.models.items_models.EpisodeItemDomain
import kotlinx.coroutines.flow.Flow

interface EpisodeRepositoryInterface {

    fun getEpisodes(
        name: String,
        episode: String
    ): Flow<PagingData<EpisodeItemDomain>>

    fun getAllEpisodesById(itemsId: List<Int>): Flow<PagingData<EpisodeItemDomain>>
}