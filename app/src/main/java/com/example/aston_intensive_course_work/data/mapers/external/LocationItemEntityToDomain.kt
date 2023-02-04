package com.example.aston_intensive_course_work.data.mapers.external

import com.example.aston_intensive_course_work.data.models.data_base_models.LocationItemEntity
import com.example.aston_intensive_course_work.domain.models.items_models.LocationItemDomain

object LocationItemEntityToDomain {
    fun map(locationItemEntity: LocationItemEntity): LocationItemDomain = LocationItemDomain(
        id = locationItemEntity.locationId,
        name = locationItemEntity.locationName,
        type = locationItemEntity.locationType,
        dimension = locationItemEntity.locationDimension,
        residents = locationItemEntity.locationResidents,
        url = locationItemEntity.locationUrl,
        created = locationItemEntity.locationCreated
    )
}