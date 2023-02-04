package com.example.aston_intensive_course_work.data.mapers.iternals

import com.example.aston_intensive_course_work.data.models.data_base_models.LocationItemEntity
import com.example.aston_intensive_course_work.data.models.network_models.LocationItemResponse

object LocationItemResponseToEntity {
    fun map(locationItemResponse: LocationItemResponse): LocationItemEntity = LocationItemEntity(
        locationId = locationItemResponse.id,
        locationName = locationItemResponse.name,
        locationType = locationItemResponse.type,
        locationDimension = locationItemResponse.dimension,
        locationResidents = locationItemResponse.residents,
        locationUrl = locationItemResponse.url,
        locationCreated = locationItemResponse.created
    )
}