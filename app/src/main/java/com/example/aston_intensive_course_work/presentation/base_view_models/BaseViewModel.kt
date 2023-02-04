package com.example.aston_intensive_course_work.presentation.base_view_models

import androidx.lifecycle.ViewModel
import java.io.IOException

abstract class BaseViewModel : ViewModel() {

    fun onErrorOccur(error: Throwable) {
        if (error is IOException) {
            getItemsList()
        }
    }

    abstract fun getItemsList()
}