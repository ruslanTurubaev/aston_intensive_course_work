package com.example.aston_intensive_course_work.presentation.fragments.list_fragments

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import com.example.aston_intensive_course_work.presentation.base_view_models.ItemsListBaseViewModel
import com.example.aston_intensive_course_work.presentation.mapers.LocationDomainToLocationItem
import com.example.aston_intensive_course_work.presentation.mapers.LocationFilterToLocationFilterDomain
import com.example.aston_intensive_course_work.presentation.models.filter_models.LocationFilters
import com.example.aston_intensive_course_work.presentation.models.items_models.LocationItem
import com.example.aston_intensive_course_work.domain.use_cases.GetLocationsUseCase
import com.example.aston_intensive_course_work.presentation.locationItemFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationListViewModel @Inject constructor(private var getLocationsUseCase: GetLocationsUseCase) :
    ItemsListBaseViewModel<LocationItem, LocationFilters>() {

    override var filtersSet = LocationFilters(
        name = "",
        type = "",
        dimension = ""
    )

    init {
        getItemsList()
    }

    override fun getItemsList() {
        viewModelScope.launch {
            getLocationsUseCase.execute(
                locationFilters = LocationFilterToLocationFilterDomain.map(
                    filtersSet
                )
            )
                .flowOn(Dispatchers.IO).cachedIn(viewModelScope).map { pd ->
                    pd.map { LocationDomainToLocationItem.map(it) }.filter { location ->
                        locationItemFilter(
                            location = location,
                            query = query
                        )
                    }
                }.collectLatest {
                    mutableItemsList.value = it
                }
        }
    }
}