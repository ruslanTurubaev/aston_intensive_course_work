package com.example.aston_intensive_course_work.presentation.mapers

import com.example.aston_intensive_course_work.presentation.models.filter_models.EpisodeFilter
import com.example.aston_intensive_course_work.domain.models.filters_models.EpisodeFilterDomain

object EpisodeFiltersToEpisodeFiltersDomain {
    fun map(episodeFilters: EpisodeFilter): EpisodeFilterDomain = EpisodeFilterDomain(
        name = episodeFilters.name,
        episode = episodeFilters.episode
    )
}