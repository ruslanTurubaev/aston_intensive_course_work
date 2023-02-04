package com.example.aston_intensive_course_work.data.mapers.external

import com.example.aston_intensive_course_work.data.models.network_models.LocationItemResponse
import com.example.aston_intensive_course_work.domain.models.items_models.LocationItemDomain

object LocationItemResponseToDomain {
    fun map(locationItemResponse: LocationItemResponse): LocationItemDomain = LocationItemDomain(
        id = locationItemResponse.id,
        name = locationItemResponse.name,
        type = locationItemResponse.type,
        dimension = locationItemResponse.dimension,
        residents = locationItemResponse.residents,
        url = locationItemResponse.url,
        created = locationItemResponse.created
    )
}