package com.example.aston_intensive_course_work.data.paging

import android.net.ConnectivityManager
import androidx.room.withTransaction
import com.example.aston_intensive_course_work.data.client.RetrofitService
import com.example.aston_intensive_course_work.data.data_base.CacheDataBase
import com.example.aston_intensive_course_work.data.extensions.isStorageDurationExpired
import com.example.aston_intensive_course_work.data.extensions.setUpdateTimeIfNeeded
import com.example.aston_intensive_course_work.data.mapers.iternals.LocationItemResponseToEntity
import com.example.aston_intensive_course_work.data.models.data_base_models.LocationItemEntity

class GetLocationsPagingSource(
    private val retrofitService: RetrofitService,
    connectivityManager: ConnectivityManager,
    cache: CacheDataBase,
    private val name: String,
    private val type: String,
    private val dimension: String
) : BaseUnitPagingSource<LocationItemEntity>(
    cache = cache,
    connectivityManager = connectivityManager
) {

    override suspend fun getItemsRemote(params: LoadParams<Int>): LoadResult<Int, LocationItemEntity> {
        val pageNumber = params.key ?: 1

        val response = retrofitService.getLocationsWithFilters(
            page = pageNumber,
            name = name,
            type = type,
            dimension = dimension
        )

        val responseCode = response.code()
        val responseBody = response.body()

        if (responseCode == SUCCESS_CODE && responseBody != null) {
            val locations = responseBody.results.map { location ->
                LocationItemResponseToEntity.map(
                    location
                )
            }

            cache.withTransaction {
                if (cache.isStorageDurationExpired()) refreshCache()
                locationDao.insertAll(locations.also {
                    cache.setUpdateTimeIfNeeded()
                })
            }

            return LoadResult.Page(
                data = locations,
                prevKey = if (pageNumber == 1) null else pageNumber,
                nextKey = if (responseBody.info.next == null) null else pageNumber + 1
            )
        } else {
            return LoadResult.Page(
                data = emptyList(),
                prevKey = if (pageNumber == 1) null else pageNumber,
                nextKey = null
            )
        }
    }

    override suspend fun getItemsLocal(
        params: LoadParams<Int>
    ): LoadResult<Int, LocationItemEntity> {
        val pageNumber = params.key ?: 1

        var locations = emptyList<LocationItemEntity>()

        cache.withTransaction {
            locations = locationDao.getLocationsWithFilters(
                limit = params.loadSize,
                offset = (pageNumber - 1) * params.loadSize,
                name = name.ifEmpty { null },
                type = type.ifEmpty { null },
                dimension = dimension.ifEmpty { null }
            )
        }

        return LoadResult.Page(
            data = locations,
            prevKey = if (pageNumber == 1) null else pageNumber,
            nextKey = if (locations.size < params.loadSize) null else pageNumber + 1
        )
    }
}