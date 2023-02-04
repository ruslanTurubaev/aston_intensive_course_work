package com.example.aston_intensive_course_work.presentation.mapers

import com.example.aston_intensive_course_work.presentation.models.items_models.LocationSimpleItem
import com.example.aston_intensive_course_work.domain.models.items_models.LocationSimpleItemDomain

object LocationSimpleDomainToLocationSimpleItem {
    fun map(locationSimpleItemDomain: LocationSimpleItemDomain): LocationSimpleItem =
        LocationSimpleItem(
            name = locationSimpleItemDomain.name,
            url = locationSimpleItemDomain.url
        )
}