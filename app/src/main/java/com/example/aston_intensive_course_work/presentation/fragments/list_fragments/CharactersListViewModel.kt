package com.example.aston_intensive_course_work.presentation.fragments.list_fragments

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import com.example.aston_intensive_course_work.presentation.base_view_models.ItemsListBaseViewModel
import com.example.aston_intensive_course_work.presentation.mapers.CharacterDomainToCharacterItem
import com.example.aston_intensive_course_work.presentation.mapers.CharacterFiltersToCharacterFiltersDomain
import com.example.aston_intensive_course_work.presentation.models.filter_models.CharacterFilters
import com.example.aston_intensive_course_work.presentation.models.items_models.CharacterItem
import com.example.aston_intensive_course_work.domain.use_cases.GetCharactersUseCase
import com.example.aston_intensive_course_work.presentation.characterItemFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersListViewModel @Inject constructor(private val getCharactersUseCase: GetCharactersUseCase) :
    ItemsListBaseViewModel<CharacterItem, CharacterFilters>() {

    override var filtersSet = CharacterFilters(
        name = "",
        status = "",
        species = "",
        type = "",
        gender = ""
    )

    init {
        getItemsList()
    }

    override fun getItemsList() {
        viewModelScope.launch {
            getCharactersUseCase.execute(
                characterFilters = CharacterFiltersToCharacterFiltersDomain.map(
                    filtersSet
                )
            )
                .flowOn(Dispatchers.IO).cachedIn(viewModelScope).map { pd ->
                    pd.map { CharacterDomainToCharacterItem.map(it) }.filter { character ->
                        characterItemFilter(
                            character = character,
                            query = query
                        )
                    }
                }.collectLatest {
                    mutableItemsList.value = it
                }
        }
    }
}

