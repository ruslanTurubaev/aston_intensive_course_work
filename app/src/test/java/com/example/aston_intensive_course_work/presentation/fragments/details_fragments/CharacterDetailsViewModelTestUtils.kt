package com.example.aston_intensive_course_work.presentation.fragments.details_fragments

import androidx.recyclerview.widget.DiffUtil
import com.example.aston_intensive_course_work.domain.models.items_models.EpisodeItemDomain
import com.example.aston_intensive_course_work.presentation.models.items_models.CharacterItem
import com.example.aston_intensive_course_work.presentation.models.items_models.EpisodeItem
import com.example.aston_intensive_course_work.presentation.models.items_models.LocationSimpleItem
import java.net.UnknownHostException

val TEST_LIST = listOf("1", "2", "3")

val TEST_CHARACTER = CharacterItem(
    id = 1,
    name = "Rick Sanchez",
    status = "Alive",
    species = "Human",
    type = "",
    gender = "Male",
    origin = LocationSimpleItem(name = "Earth (C-137)", url = "https://rickandmortyapi.com/api/location/1"),
    location = LocationSimpleItem(name = "Citadel of Ricks", url = "https://rickandmortyapi.com/api/location/3"),
    image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
    episode = TEST_LIST,
    url = "https://rickandmortyapi.com/api/character/1",
    created = "2017-11-04T18:48:46.250Z"
)

val episode1 = EpisodeItem(
    id = 1,
    name = "Pilot",
    airDate = "December 2, 2013",
    episode = "S01E01",
    characters = emptyList(),
    url = "https://rickandmortyapi.com/api/episode/1",
    created = "2017-11-10T12:56:33.798Z"
)

val episode2 = EpisodeItem(
    id = 2,
    name = "Lawnmower Dog",
    airDate = "December 9, 2013",
    episode = "S01E02",
    characters = emptyList(),
    url = "https://rickandmortyapi.com/api/episode/2",
    created = "2017-11-10T12:56:33.916Z"
)

val episode3 = EpisodeItem(
    id = 3,
    name = "Anatomy Park",
    airDate = "December 16, 2013",
    episode = "S01E03",
    characters = emptyList(),
    url = "https://rickandmortyapi.com/api/episode/3",
    created = "2017-11-10T12:56:34.022Z"
)

val episodeDomain1 = EpisodeItemDomain(
    id = 1,
    name = "Pilot",
    airDate = "December 2, 2013",
    episode = "S01E01",
    characters = emptyList(),
    url = "https://rickandmortyapi.com/api/episode/1",
    created = "2017-11-10T12:56:33.798Z"
)

val episodeDomain2 = EpisodeItemDomain(
    id = 2,
    name = "Lawnmower Dog",
    airDate = "December 9, 2013",
    episode = "S01E02",
    characters = emptyList(),
    url = "https://rickandmortyapi.com/api/episode/2",
    created = "2017-11-10T12:56:33.916Z"
)

val episodeDomain3 = EpisodeItemDomain(
    id = 3,
    name = "Anatomy Park",
    airDate = "December 16, 2013",
    episode = "S01E03",
    characters = emptyList(),
    url = "https://rickandmortyapi.com/api/episode/3",
    created = "2017-11-10T12:56:34.022Z"
)

val TEST_EPISODE_LIST = listOf(episode1, episode2, episode3)
val TEST_EPISODE_DOMAIN_LIST = listOf(episodeDomain1, episodeDomain2, episodeDomain3)
val EMPTY_LIST = emptyList<EpisodeItem>()

val TEST_ERROR = Exception()

val TEST_UNKNOWN_HOST_EXCEPTION = UnknownHostException()

class MockEpisodeItemDiffCallback : DiffUtil.ItemCallback<EpisodeItem>() {
    override fun areItemsTheSame(oldItem: EpisodeItem, newItem: EpisodeItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: EpisodeItem, newItem: EpisodeItem): Boolean {
        return oldItem == newItem
    }
}