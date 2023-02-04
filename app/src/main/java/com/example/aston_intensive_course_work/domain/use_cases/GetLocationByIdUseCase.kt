package com.example.aston_intensive_course_work.domain.use_cases

import com.example.aston_intensive_course_work.domain.interfaces.LocationRepositoryInterface
import com.example.aston_intensive_course_work.domain.models.items_models.LocationItemDomain
import javax.inject.Inject

class GetLocationByIdUseCase @Inject constructor(private val repository: LocationRepositoryInterface) {

    suspend fun execute(locationUrl: String): LocationItemDomain {
        val list = locationUrl.split("/")
        val locationId = list[list.size - 1].toInt()

        return repository.getLocationById(locationId = locationId)
    }
}