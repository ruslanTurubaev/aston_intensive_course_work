package com.example.aston_intensive_course_work.domain.interfaces

import androidx.paging.PagingData
import com.example.aston_intensive_course_work.domain.models.items_models.LocationItemDomain
import kotlinx.coroutines.flow.Flow

interface LocationRepositoryInterface {

    fun getLocations(
        name: String,
        type: String,
        dimension: String
    ): Flow<PagingData<LocationItemDomain>>

    suspend fun getLocationById(locationId: Int): LocationItemDomain
}