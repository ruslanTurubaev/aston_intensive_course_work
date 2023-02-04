package com.example.aston_intensive_course_work.presentation.di

import com.example.aston_intensive_course_work.presentation.fragments.base_fragments.BaseFragment
import com.example.aston_intensive_course_work.presentation.fragments.base_fragments.FiltersBaseFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(fragment: BaseFragment)
    fun inject(fragment: FiltersBaseFragment)
}