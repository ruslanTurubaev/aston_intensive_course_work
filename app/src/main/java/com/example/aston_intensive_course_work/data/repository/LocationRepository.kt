package com.example.aston_intensive_course_work.data.repository

import android.net.ConnectivityManager
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.aston_intensive_course_work.data.client.RetrofitService
import com.example.aston_intensive_course_work.data.data_base.CacheDataBase
import com.example.aston_intensive_course_work.data.extensions.hasInternetConnection
import com.example.aston_intensive_course_work.data.mapers.external.LocationItemEntityToDomain
import com.example.aston_intensive_course_work.data.mapers.external.LocationItemResponseToDomain
import com.example.aston_intensive_course_work.data.paging.GetLocationsPagingSource
import com.example.aston_intensive_course_work.domain.interfaces.LocationRepositoryInterface
import com.example.aston_intensive_course_work.domain.models.items_models.LocationItemDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocationRepository(
    private val retrofit: RetrofitService,
    private val cache: CacheDataBase,
    private val connectivityManager: ConnectivityManager
) : LocationRepositoryInterface {

    override fun getLocations(
        name: String,
        type: String,
        dimension: String
    ): Flow<PagingData<LocationItemDomain>> {
        return Pager(
            config = PagingConfig(
                pageSize = 18,
                prefetchDistance = 9,
                enablePlaceholders = false,
                initialLoadSize = 18
            )
        ) {
            GetLocationsPagingSource(
                retrofitService = retrofit,
                connectivityManager = connectivityManager,
                cache = cache,
                name = name,
                type = type,
                dimension = dimension
            )
        }.flow.map { pd ->
            pd.map { location ->
                LocationItemEntityToDomain.map(
                    location
                )
            }
        }
    }

    override suspend fun getLocationById(locationId: Int): LocationItemDomain {
        return if (connectivityManager.hasInternetConnection()) {
            try {
                LocationItemResponseToDomain.map(retrofit.getLocationById(locationId = locationId))
            } catch (exception: Exception) {
                LocationItemEntityToDomain.map(
                    cache.locationDao().getLocationById(locationId = locationId)
                )
            }
        } else {
            LocationItemEntityToDomain.map(
                cache.locationDao().getLocationById(locationId = locationId)
            )
        }
    }
}