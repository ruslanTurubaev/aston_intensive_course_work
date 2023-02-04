package com.example.aston_intensive_course_work.presentation.mapers

import com.example.aston_intensive_course_work.presentation.models.filter_models.LocationFilters
import com.example.aston_intensive_course_work.domain.models.filters_models.LocationFiltersDomain

object LocationFilterToLocationFilterDomain {
    fun map(locationFilters: LocationFilters): LocationFiltersDomain = LocationFiltersDomain(
        name = locationFilters.name,
        type = locationFilters.type,
        dimension = locationFilters.dimension
    )
}