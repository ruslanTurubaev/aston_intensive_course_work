package com.example.aston_intensive_course_work.presentation.fragments.details_fragments

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.aston_intensive_course_work.presentation.base_view_models.ItemDetailsBaseViewModel
import com.example.aston_intensive_course_work.presentation.mapers.EpisodeDomainToEpisodeItem
import com.example.aston_intensive_course_work.presentation.models.items_models.CharacterItem
import com.example.aston_intensive_course_work.presentation.models.items_models.EpisodeItem
import com.example.aston_intensive_course_work.domain.use_cases.GetAllEpisodesByIdsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterDetailsViewModel @Inject constructor(private var getAllEpisodesByIdsUseCase: GetAllEpisodesByIdsUseCase) :
    ItemDetailsBaseViewModel<CharacterItem, EpisodeItem>() {

    override fun getItemsList() {
        viewModelScope.launch {
            getAllEpisodesByIdsUseCase.execute(item.episode).flowOn(Dispatchers.IO)
                .cachedIn(viewModelScope).map { pd ->
                    pd.map { episode ->
                        EpisodeDomainToEpisodeItem.map(episodeItemDomain = episode)
                    }
                }.collectLatest {
                    mutableAttachedItemsList.value = it
                }
        }
    }
}