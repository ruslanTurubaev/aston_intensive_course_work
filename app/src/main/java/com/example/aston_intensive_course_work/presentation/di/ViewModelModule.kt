package com.example.aston_intensive_course_work.presentation.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aston_intensive_course_work.presentation.MainViewModel
import com.example.aston_intensive_course_work.presentation.fragments.details_fragments.CharacterDetailsViewModel
import com.example.aston_intensive_course_work.presentation.fragments.details_fragments.EpisodeDetailsViewModel
import com.example.aston_intensive_course_work.presentation.fragments.details_fragments.LocationDetailsViewModel
import com.example.aston_intensive_course_work.presentation.fragments.list_fragments.CharactersListViewModel
import com.example.aston_intensive_course_work.presentation.fragments.list_fragments.EpisodesListViewModel
import com.example.aston_intensive_course_work.presentation.fragments.list_fragments.LocationListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun splashMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CharacterDetailsViewModel::class)
    abstract fun splashCharacterDetailsViewModel(viewModel: CharacterDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EpisodeDetailsViewModel::class)
    abstract fun splashEpisodeDetailsViewModel(viewModel: EpisodeDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LocationDetailsViewModel::class)
    abstract fun splashLocationDetailsViewModel(viewModel: LocationDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CharactersListViewModel::class)
    abstract fun splashCharactersListViewModel(viewModel: CharactersListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EpisodesListViewModel::class)
    abstract fun splashEpisodesListViewModel(viewModel: EpisodesListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LocationListViewModel::class)
    abstract fun splashLocationListViewModel(viewModel: LocationListViewModel): ViewModel
}