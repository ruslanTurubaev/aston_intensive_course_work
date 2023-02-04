package com.example.aston_intensive_course_work.presentation.app

import android.app.Application
import com.example.aston_intensive_course_work.presentation.di.AppComponent
import com.example.aston_intensive_course_work.presentation.di.DaggerAppComponent
import com.example.aston_intensive_course_work.presentation.di.DataModule

class App : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder().dataModule(DataModule(context = this)).build()
    }
}