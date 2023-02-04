package com.example.aston_intensive_course_work.presentation.fragments.list_fragments

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.example.aston_intensive_course_work.domain.models.filters_models.CharacterFiltersDomain
import com.example.aston_intensive_course_work.domain.models.items_models.CharacterItemDomain
import com.example.aston_intensive_course_work.domain.models.items_models.LocationSimpleItemDomain
import com.example.aston_intensive_course_work.presentation.models.filter_models.CharacterFilters
import com.example.aston_intensive_course_work.presentation.models.items_models.CharacterItem
import com.example.aston_intensive_course_work.presentation.models.items_models.LocationSimpleItem
import java.net.UnknownHostException

val TEST_ERROR = Exception()

val TEST_UNKNOWN_HOST_EXCEPTION = UnknownHostException()

val EMPTY_LIST = emptyList<CharacterItem>()

const val TEST_QUERY = "Smith"

val TEST_FILTERS = CharacterFilters(
    name = "test_name",
    status = "test_status",
    species = "test_species",
    type = "test_type",
    gender = "test_gender"
)

val TEST_FILTERS_DOMAIN = CharacterFiltersDomain(
    name = "test_name",
    status = "test_status",
    species = "test_species",
    type = "test_type",
    gender = "test_gender"
)

val EMPTY_FILTERS = CharacterFilters(
    name = "",
    status = "",
    species = "",
    type = "",
    gender = ""
)

val EMPTY_FILTERS_DOMAIN = CharacterFiltersDomain(
    name = "",
    status = "",
    species = "",
    type = "",
    gender = ""
)

class MockListCallback : ListUpdateCallback {
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
}

class MockDiffCallback : DiffUtil.ItemCallback<CharacterItem>() {
    override fun areItemsTheSame(oldItem: CharacterItem, newItem: CharacterItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CharacterItem, newItem: CharacterItem): Boolean {
        return oldItem == newItem
    }
}

val character1 = CharacterItem(
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

val character2 = CharacterItem(
    id = 2,
    name = "Morty Smith",
    status = "Alive",
    species = "Human",
    type = "",
    gender = "Male",
    origin = LocationSimpleItem(name = "unknown", url = ""),
    location = LocationSimpleItem(name = "Citadel of Ricks", url = "https://rickandmortyapi.com/api/location/3"),
    image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
    episode = emptyList(),
    url = "https://rickandmortyapi.com/api/character/2",
    created = "2017-11-04T18:50:21.651Z"
)

val character3 = CharacterItem(
    id = 3,
    name = "Summer Smith",
    status = "Alive",
    species = "Human",
    type = "",
    gender = "Female",
    origin = LocationSimpleItem(name = "Earth (Replacement Dimension)", url = "https://rickandmortyapi.com/api/location/20"),
    location = LocationSimpleItem(name = "Earth (Replacement Dimension)", url = "https://rickandmortyapi.com/api/location/20"),
    image = "https://rickandmortyapi.com/api/character/avatar/3.jpeg",
    episode = emptyList(),
    url = "https://rickandmortyapi.com/api/character/3",
    created = "2017-11-04T19:09:56.428Z"
)

val character4 = CharacterItem(
    id = 4,
    name = "Beth Smith",
    status = "Alive",
    species = "Human",
    type = "",
    gender = "Female",
    origin = LocationSimpleItem(name = "Earth (Replacement Dimension)", url = "https://rickandmortyapi.com/api/location/20"),
    location = LocationSimpleItem(name = "Earth (Replacement Dimension)", url = "https://rickandmortyapi.com/api/location/20"),
    image = "https://rickandmortyapi.com/api/character/avatar/4.jpeg",
    episode = emptyList(),
    url = "https://rickandmortyapi.com/api/character/4",
    created = "2017-11-04T19:22:43.665Z"
)

val characterDomain1 = CharacterItemDomain(
    id = 1,
    name = "Rick Sanchez",
    status = "Alive",
    species = "Human",
    type = "",
    gender = "Male",
    origin = LocationSimpleItemDomain(name = "Earth (C-137)", url = "https://rickandmortyapi.com/api/location/1"),
    location = LocationSimpleItemDomain(name = "Citadel of Ricks", url = "https://rickandmortyapi.com/api/location/3"),
    image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
    episode = emptyList(),
    url = "https://rickandmortyapi.com/api/character/1",
    created = "2017-11-04T18:48:46.250Z"
)

val characterDomain2 = CharacterItemDomain(
    id = 2,
    name = "Morty Smith",
    status = "Alive",
    species = "Human",
    type = "",
    gender = "Male",
    origin = LocationSimpleItemDomain(name = "unknown", url = ""),
    location = LocationSimpleItemDomain(name = "Citadel of Ricks", url = "https://rickandmortyapi.com/api/location/3"),
    image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
    episode = emptyList(),
    url = "https://rickandmortyapi.com/api/character/2",
    created = "2017-11-04T18:50:21.651Z"
)

val characterDomain3 = CharacterItemDomain(
    id = 3,
    name = "Summer Smith",
    status = "Alive",
    species = "Human",
    type = "",
    gender = "Female",
    origin = LocationSimpleItemDomain(name = "Earth (Replacement Dimension)", url = "https://rickandmortyapi.com/api/location/20"),
    location = LocationSimpleItemDomain(name = "Earth (Replacement Dimension)", url = "https://rickandmortyapi.com/api/location/20"),
    image = "https://rickandmortyapi.com/api/character/avatar/3.jpeg",
    episode = emptyList(),
    url = "https://rickandmortyapi.com/api/character/3",
    created = "2017-11-04T19:09:56.428Z"
)

val characterDomain4 = CharacterItemDomain(
    id = 4,
    name = "Beth Smith",
    status = "Alive",
    species = "Human",
    type = "",
    gender = "Female",
    origin = LocationSimpleItemDomain(name = "Earth (Replacement Dimension)", url = "https://rickandmortyapi.com/api/location/20"),
    location = LocationSimpleItemDomain(name = "Earth (Replacement Dimension)", url = "https://rickandmortyapi.com/api/location/20"),
    image = "https://rickandmortyapi.com/api/character/avatar/4.jpeg",
    episode = emptyList(),
    url = "https://rickandmortyapi.com/api/character/4",
    created = "2017-11-04T19:22:43.665Z"
)

val TESTED_CHARACTERS_LIST = listOf(character1, character2, character3, character4)
val TESTED_CHARACTERS_FILTERED_LIST = listOf(character2, character3, character4)
val TESTED_CHARACTERS_DOMAIN_LIST = listOf(characterDomain1, characterDomain2, characterDomain3, characterDomain4)
