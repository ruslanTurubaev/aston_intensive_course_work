package com.example.aston_intensive_course_work.presentation.mapers

import com.example.aston_intensive_course_work.presentation.models.items_models.EpisodeItem
import com.example.aston_intensive_course_work.domain.models.items_models.EpisodeItemDomain

object EpisodeDomainToEpisodeItem {
    fun map(episodeItemDomain: EpisodeItemDomain): EpisodeItem = EpisodeItem(
        id = episodeItemDomain.id,
        name = episodeItemDomain.name,
        airDate = episodeItemDomain.airDate,
        episode = episodeItemDomain.episode,
        characters = episodeItemDomain.characters,
        url = episodeItemDomain.url,
        created = episodeItemDomain.created
    )
}