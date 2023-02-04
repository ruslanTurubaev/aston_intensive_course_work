package com.example.aston_intensive_course_work.data.extensions

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.paging.PagingSource
import com.example.aston_intensive_course_work.data.data_base.CacheDataBase
import com.example.aston_intensive_course_work.data.data_base.STORAGE_DURATION
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T> Gson.convertFromJson(json: String): T =
    fromJson(json, object : TypeToken<T>() {}.type)

fun CacheDataBase.isStorageDurationExpired(): Boolean {
    return if (lastTimeUpdated != null) {
        System.currentTimeMillis() > lastTimeUpdated!! + STORAGE_DURATION
    } else {
        false
    }
}

fun CacheDataBase.setUpdateTimeIfNeeded() {
    if (lastTimeUpdated == null) setUpdateTime(System.currentTimeMillis())
}

fun ConnectivityManager.hasInternetConnection(): Boolean {
    val network = this.activeNetwork
    val capabilities = this.getNetworkCapabilities(network)
    return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
}

fun <T : Any> List<T>.getPageToLoad(params: PagingSource.LoadParams<Int>): List<T> {
    val pageNumber = params.key ?: 1
    val loadSize = params.loadSize

    val startIndex = (pageNumber - 1) * loadSize
    val stopIndex = if (startIndex + loadSize < this.size) {
        startIndex + loadSize
    } else {
        this.size
    }

    return this.subList(startIndex, stopIndex)
}