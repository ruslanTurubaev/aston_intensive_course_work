package com.example.aston_intensive_course_work.presentation.fragments.details_fragments

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.aston_intensive_course_work.presentation.base_view_models.ItemDetailsBaseViewModel
import com.example.aston_intensive_course_work.presentation.mapers.CharacterDomainToCharacterItem
import com.example.aston_intensive_course_work.presentation.models.items_models.CharacterItem
import com.example.aston_intensive_course_work.presentation.models.items_models.EpisodeItem
import com.example.aston_intensive_course_work.domain.use_cases.GetAllCharactersByIdsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodeDetailsViewModel @Inject constructor(private var getAllCharactersByIdsUseCase: GetAllCharactersByIdsUseCase) :
    ItemDetailsBaseViewModel<EpisodeItem, CharacterItem>() {

    override fun getItemsList() {
        viewModelScope.launch {
            getAllCharactersByIdsUseCase.execute(item.characters).flowOn(Dispatchers.IO)
                .cachedIn(viewModelScope).map { pd ->
                    pd.map { character ->
                        CharacterDomainToCharacterItem.map(characterItemDomain = character)
                    }
                }.collectLatest {
                    mutableAttachedItemsList.value = it
                }
        }
    }
}