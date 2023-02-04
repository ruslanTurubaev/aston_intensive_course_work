package com.example.aston_intensive_course_work.presentation.fragments.list_fragments

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import com.example.aston_intensive_course_work.presentation.base_view_models.ItemsListBaseViewModel
import com.example.aston_intensive_course_work.presentation.mapers.EpisodeDomainToEpisodeItem
import com.example.aston_intensive_course_work.presentation.mapers.EpisodeFiltersToEpisodeFiltersDomain
import com.example.aston_intensive_course_work.presentation.models.filter_models.EpisodeFilter
import com.example.aston_intensive_course_work.presentation.models.items_models.EpisodeItem
import com.example.aston_intensive_course_work.domain.use_cases.GetEpisodesUseCase
import com.example.aston_intensive_course_work.presentation.episodeItemFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodesListViewModel @Inject constructor(private var getEpisodesUseCase: GetEpisodesUseCase) :
    ItemsListBaseViewModel<EpisodeItem, EpisodeFilter>() {

    override var filtersSet = EpisodeFilter(
        name = "",
        episode = ""
    )

    init {
        getItemsList()
    }

    override fun getItemsList() {
        viewModelScope.launch {
            getEpisodesUseCase.execute(
                episodeFilter = EpisodeFiltersToEpisodeFiltersDomain.map(
                    filtersSet
                )
            )
                .flowOn(Dispatchers.IO).cachedIn(viewModelScope).map { pd ->
                    pd.map { EpisodeDomainToEpisodeItem.map(it) }.filter { episode ->
                        episodeItemFilter(
                            episode = episode,
                            query = query
                        )
                    }
                }.collectLatest {
                    mutableItemsList.value = it
                }
        }
    }
}