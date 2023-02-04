package com.example.aston_intensive_course_work.data.mapers.iternals

import com.example.aston_intensive_course_work.data.models.data_base_models.EpisodeItemEntity
import com.example.aston_intensive_course_work.data.models.network_models.EpisodeItemResponse

object EpisodeItemResponseToEntity {
    fun map(episodeItemResponse: EpisodeItemResponse): EpisodeItemEntity = EpisodeItemEntity(
        episodeId = episodeItemResponse.id,
        episodeName = episodeItemResponse.name,
        episodeAirDate = episodeItemResponse.airDate,
        episodeNumber = episodeItemResponse.episode,
        episodeCharacters = episodeItemResponse.characters,
        episodeUrl = episodeItemResponse.url,
        episodeCreated = episodeItemResponse.created
    )
}