package com.example.aston_intensive_course_work.presentation.base_view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.example.aston_intensive_course_work.presentation.interfaces.Filter
import com.example.aston_intensive_course_work.presentation.interfaces.Item

abstract class ItemsListBaseViewModel<I : Item, F : Filter> : BaseViewModel() {

    protected var query = ""
    abstract var filtersSet: F

    protected var mutableItemsList = MutableLiveData<PagingData<I>>()

    val itemsList: LiveData<PagingData<I>> get() = mutableItemsList
    val charactersFilterSet: F get() = filtersSet

    fun onUserQueryInput(query: String) {
        this.query = query
        getItemsList()
    }

    fun getCurrentQuery(): String {
        return query
    }

    fun onUserFiltersApply(filters: F) {
        filtersSet = filters
        getItemsList()
    }
}