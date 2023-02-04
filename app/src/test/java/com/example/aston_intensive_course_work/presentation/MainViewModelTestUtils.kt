package com.example.aston_intensive_course_work.presentation

import com.example.aston_intensive_course_work.domain.models.items_models.LocationItemDomain
import com.example.aston_intensive_course_work.presentation.models.items_models.CharacterItem
import com.example.aston_intensive_course_work.presentation.models.items_models.EpisodeItem
import com.example.aston_intensive_course_work.presentation.models.items_models.LocationItem
import com.example.aston_intensive_course_work.presentation.models.items_models.LocationSimpleItem

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
    episode = emptyList(),
    url = "https://rickandmortyapi.com/api/character/1",
    created = "2017-11-04T18:48:46.250Z"
)

val TEST_EPISODE = EpisodeItem(
    id = 1,
    name = "Pilot",
    airDate = "December 2, 2013",
    episode = "S01E01",
    characters = emptyList(),
    url = "https://rickandmortyapi.com/api/episode/1",
    created = "2017-11-10T12:56:33.798Z"
)

val TEST_LOCATION = LocationItem(
    id = 1,
    name = "Earth",
    type = "Planet",
    dimension = "Dimension C-137",
    residents = emptyList(),
    url = "https://rickandmortyapi.com/api/location/1",
    created = "2017-11-10T12:42:04.162Z"
)

val TEST_LOCATION_DOMAIN = LocationItemDomain(
    id = 1,
    name = "Earth",
    type = "Planet",
    dimension = "Dimension C-137",
    residents = emptyList(),
    url = "https://rickandmortyapi.com/api/location/1",
    created = "2017-11-10T12:42:04.162Z"
)

val TEST_GENDER_FILTER = "Male"

val TEST_STATUS_FILTER = "Alive"

val TEST_LOCATION_URL = "https://rickandmortyapi.com/api/location/1"

