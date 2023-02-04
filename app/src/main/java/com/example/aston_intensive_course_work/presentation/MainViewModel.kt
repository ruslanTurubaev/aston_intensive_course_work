package com.example.aston_intensive_course_work.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aston_intensive_course_work.presentation.mapers.LocationDomainToLocationItem
import com.example.aston_intensive_course_work.presentation.models.items_models.CharacterItem
import com.example.aston_intensive_course_work.presentation.models.items_models.EpisodeItem
import com.example.aston_intensive_course_work.presentation.models.items_models.LocationItem
import com.example.aston_intensive_course_work.domain.use_cases.GetLocationByIdUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private var getLocationByIdsUseCase: GetLocationByIdUseCase) :
    ViewModel() {

    private var _selectedCharacter: CharacterItem? = null
    private var _selectedLocation = MutableLiveData<LocationItem?>(null)
    private var _selectedEpisode: EpisodeItem? = null
    private var mutableGenderFilter: MutableLiveData<String> = MutableLiveData("")
    private var mutableStatusFilter: MutableLiveData<String> = MutableLiveData("")

    fun onUserSelectLocationOnCharacterCard(locationUrl: String) {
        viewModelScope.launch {
            val location = getLocationByIdsUseCase.execute(locationUrl)
            _selectedLocation.value = LocationDomainToLocationItem.map(location)
        }
    }

    val selectedCharacter: CharacterItem? get() = _selectedCharacter
    val selectedLocation: LiveData<LocationItem?> get() = _selectedLocation
    val selectedEpisode: EpisodeItem? get() = _selectedEpisode
    val liveDataGenderFilters: LiveData<String> get() = mutableGenderFilter
    val liveDataStatusFilters: LiveData<String> get() = mutableStatusFilter

    fun onUserSelectCharacter(characterItem: CharacterItem) {
        _selectedCharacter = characterItem
    }

    fun onUserSelectLocation(locationItem: LocationItem?) {
        _selectedLocation.value = locationItem
    }

    fun onUserSelectEpisode(episodeItem: EpisodeItem) {
        _selectedEpisode = episodeItem
    }

    fun onUserSelectGenderFilter(genderFilter: String) {
        mutableGenderFilter.value = genderFilter
    }

    fun onUserSelectStatusFilter(statusFilter: String) {
        mutableStatusFilter.value = statusFilter
    }
}