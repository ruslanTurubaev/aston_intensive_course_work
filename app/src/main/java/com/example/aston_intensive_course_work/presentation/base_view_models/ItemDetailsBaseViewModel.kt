package com.example.aston_intensive_course_work.presentation.base_view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.example.aston_intensive_course_work.presentation.interfaces.Item

abstract class ItemDetailsBaseViewModel<I : Item, SI : Item> : BaseViewModel() {

    protected lateinit var item: I

    fun getSelectedItem(): I {
        return item
    }

    protected var mutableAttachedItemsList = MutableLiveData<PagingData<SI>>()
    val attachedItemsList: LiveData<PagingData<SI>> get() = mutableAttachedItemsList

    fun onUserSelectItem(item: I) {
        mutableAttachedItemsList = MutableLiveData()
        this.item = item
        getItemsList()
    }
}