package com.example.aston_intensive_course_work.domain.use_cases

import androidx.paging.PagingData
import com.example.aston_intensive_course_work.domain.interfaces.EpisodeRepositoryInterface
import com.example.aston_intensive_course_work.domain.models.items_models.EpisodeItemDomain
import com.example.aston_intensive_course_work.domain.models.filters_models.EpisodeFilterDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEpisodesUseCase @Inject constructor(private val repository: EpisodeRepositoryInterface) {

    fun execute(episodeFilter: EpisodeFilterDomain): Flow<PagingData<EpisodeItemDomain>> {
        val name = episodeFilter.name
        val episode = episodeFilter.episode

        return repository.getEpisodes(name = name, episode = episode)
    }
}