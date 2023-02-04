package com.example.aston_intensive_course_work.data.mapers.external

import com.example.aston_intensive_course_work.data.models.data_base_models.EpisodeItemEntity
import com.example.aston_intensive_course_work.domain.models.items_models.EpisodeItemDomain

object EpisodeItemEntityToDomain {
    fun map(episodeItemEntity: EpisodeItemEntity): EpisodeItemDomain = EpisodeItemDomain(
        id = episodeItemEntity.episodeId,
        name = episodeItemEntity.episodeName,
        airDate = episodeItemEntity.episodeAirDate,
        episode = episodeItemEntity.episodeNumber,
        characters = episodeItemEntity.episodeCharacters,
        url = episodeItemEntity.episodeUrl,
        created = episodeItemEntity.episodeCreated
    )
}