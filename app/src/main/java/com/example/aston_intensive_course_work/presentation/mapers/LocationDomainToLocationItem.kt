package com.example.aston_intensive_course_work.presentation.mapers

import com.example.aston_intensive_course_work.presentation.models.items_models.LocationItem
import com.example.aston_intensive_course_work.domain.models.items_models.LocationItemDomain

object LocationDomainToLocationItem {
    fun map(locationItemDomain: LocationItemDomain): LocationItem = LocationItem(
        id = locationItemDomain.id,
        name = locationItemDomain.name,
        type = locationItemDomain.type,
        dimension = locationItemDomain.dimension,
        residents = locationItemDomain.residents,
        url = locationItemDomain.url,
        created = locationItemDomain.created
    )
}