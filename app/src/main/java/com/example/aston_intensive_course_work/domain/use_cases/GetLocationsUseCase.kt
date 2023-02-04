package com.example.aston_intensive_course_work.domain.use_cases

import androidx.paging.PagingData
import com.example.aston_intensive_course_work.domain.interfaces.LocationRepositoryInterface
import com.example.aston_intensive_course_work.domain.models.items_models.LocationItemDomain
import com.example.aston_intensive_course_work.domain.models.filters_models.LocationFiltersDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocationsUseCase @Inject constructor(private val repository: LocationRepositoryInterface) {

    fun execute(locationFilters: LocationFiltersDomain): Flow<PagingData<LocationItemDomain>> {
        val name = locationFilters.name
        val type = locationFilters.type
        val dimension = locationFilters.dimension

        return repository.getLocations(name = name, type = type, dimension = dimension)
    }
}