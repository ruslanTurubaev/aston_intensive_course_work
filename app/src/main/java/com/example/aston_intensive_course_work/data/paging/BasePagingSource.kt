package com.example.aston_intensive_course_work.data.paging

import android.net.ConnectivityManager
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.room.withTransaction
import com.example.aston_intensive_course_work.data.data_base.CacheDataBase
import com.example.aston_intensive_course_work.data.extensions.hasInternetConnection

const val SUCCESS_CODE = 200

abstract class BaseUnitPagingSource<T : Any>(
    protected val cache: CacheDataBase,
    private val connectivityManager: ConnectivityManager
) : PagingSource<Int, T>() {

    protected val characterDao = cache.characterDao()
    protected val locationDao = cache.locationDao()
    protected val episodeDao = cache.episodeDao()

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            if (connectivityManager.hasInternetConnection()) {
                getItemsRemote(params = params)
            } else {
                getItemsLocal(params = params)
            }
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    protected suspend fun refreshCache() {
        cache.withTransaction {
            characterDao.clearAll()
            locationDao.clearAll()
            episodeDao.clearAll()

            cache.setUpdateTime(null)
        }
    }

    abstract suspend fun getItemsRemote(params: LoadParams<Int>): LoadResult<Int, T>
    abstract suspend fun getItemsLocal(params: LoadParams<Int>): LoadResult<Int, T>
}