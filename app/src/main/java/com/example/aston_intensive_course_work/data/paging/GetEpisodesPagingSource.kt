package com.example.aston_intensive_course_work.data.paging

import android.net.ConnectivityManager
import androidx.room.withTransaction
import com.example.aston_intensive_course_work.data.client.RetrofitService
import com.example.aston_intensive_course_work.data.data_base.CacheDataBase
import com.example.aston_intensive_course_work.data.extensions.isStorageDurationExpired
import com.example.aston_intensive_course_work.data.extensions.setUpdateTimeIfNeeded
import com.example.aston_intensive_course_work.data.mapers.iternals.EpisodeItemResponseToEntity
import com.example.aston_intensive_course_work.data.models.data_base_models.EpisodeItemEntity

class GetEpisodesPagingSource(
    private val retrofitService: RetrofitService,
    connectivityManager: ConnectivityManager,
    cache: CacheDataBase,
    private val name: String,
    private val episode: String
) : BaseUnitPagingSource<EpisodeItemEntity>(
    cache = cache,
    connectivityManager = connectivityManager
) {
    override suspend fun getItemsRemote(params: LoadParams<Int>): LoadResult<Int, EpisodeItemEntity> {
        val pageNumber = params.key ?: 1

        val response = retrofitService.getEpisodesWithFilters(
            page = pageNumber,
            name = name,
            episode = episode
        )

        val responseCode = response.code()
        val responseBode = response.body()

        if (responseCode == SUCCESS_CODE && responseBode != null) {
            val episodes = responseBode.results.map { episode ->
                EpisodeItemResponseToEntity.map(
                    episode
                )
            }

            cache.withTransaction {
                if (cache.isStorageDurationExpired()) refreshCache()
                episodeDao.insertAll(episodes.also {
                    cache.setUpdateTimeIfNeeded()
                })
            }

            return LoadResult.Page(
                data = episodes,
                prevKey = if (pageNumber == 1) null else pageNumber,
                nextKey = if (responseBode.info.next == null) null else pageNumber + 1
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
    ): LoadResult<Int, EpisodeItemEntity> {
        val pageNumber = params.key ?: 1

        var episodes = emptyList<EpisodeItemEntity>()

        cache.withTransaction {
            episodes = episodeDao.getEpisodesWithFilters(
                limit = params.loadSize,
                offset = (pageNumber - 1) * params.loadSize,
                name = name.ifEmpty { null },
                episode = episode.ifEmpty { null }
            )
        }

        return LoadResult.Page(
            data = episodes,
            prevKey = if (pageNumber == 1) null else pageNumber,
            nextKey = if (episodes.size < params.loadSize) null else pageNumber + 1
        )
    }
}