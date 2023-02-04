package com.example.aston_intensive_course_work.data.mapers.external

import com.example.aston_intensive_course_work.data.models.data_base_models.LocationSimpleItemEntity
import com.example.aston_intensive_course_work.domain.models.items_models.LocationSimpleItemDomain

object LocationSimpleItemEntityToDomain {
    fun map(locationSimpleItemEntity: LocationSimpleItemEntity): LocationSimpleItemDomain =
        LocationSimpleItemDomain(
            name = locationSimpleItemEntity.locationSimpleName,
            url = locationSimpleItemEntity.locationSimpleUrl
        )
}