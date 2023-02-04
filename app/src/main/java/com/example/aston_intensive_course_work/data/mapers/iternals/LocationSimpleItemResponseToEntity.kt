package com.example.aston_intensive_course_work.data.mapers.iternals

import com.example.aston_intensive_course_work.data.models.data_base_models.LocationSimpleItemEntity
import com.example.aston_intensive_course_work.data.models.network_models.LocationSimpleItemResponse

object LocationSimpleItemResponseToEntity {
    fun map(locationSimpleItemResponse: LocationSimpleItemResponse): LocationSimpleItemEntity =
        LocationSimpleItemEntity(
            locationSimpleName = locationSimpleItemResponse.name,
            locationSimpleUrl = locationSimpleItemResponse.url
        )
}