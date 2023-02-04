package com.example.aston_intensive_course_work.data.repository

import android.net.ConnectivityManager
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.aston_intensive_course_work.data.client.RetrofitService
import com.example.aston_intensive_course_work.data.data_base.CacheDataBase
import com.example.aston_intensive_course_work.data.mapers.external.EpisodeItemEntityToDomain
import com.example.aston_intensive_course_work.data.paging.GetEpisodesByIdPagingSource
import com.example.aston_intensive_course_work.data.paging.GetEpisodesPagingSource
import com.example.aston_intensive_course_work.domain.interfaces.EpisodeRepositoryInterface
import com.example.aston_intensive_course_work.domain.models.items_models.EpisodeItemDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EpisodeRepository(
    private val retrofit: RetrofitService,
    private val cache: CacheDataBase,
    private val connectivityManager: ConnectivityManager
) : EpisodeRepositoryInterface {

    override fun getEpisodes(name: String, episode: String): Flow<PagingData<EpisodeItemDomain>> {
        return Pager(
            config = PagingConfig(
                pageSize = 17,
                prefetchDistance = 8,
                enablePlaceholders = false,
                initialLoadSize = 17
            )
        ) {
            GetEpisodesPagingSource(
                retrofitService = retrofit,
                connectivityManager = connectivityManager,
                cache = cache,
                name = name,
                episode = episode
            )
        }.flow.map { pd -> pd.map { episode -> EpisodeItemEntityToDomain.map(episode) } }
    }

    override fun getAllEpisodesById(itemsId: List<Int>): Flow<PagingData<EpisodeItemDomain>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 5,
                enablePlaceholders = false,
                initialLoadSize = 10
            )
        ) {
            GetEpisodesByIdPagingSource(
                retrofitService = retrofit,
                connectivityManager = connectivityManager,
                cache = cache,
                itemsId = itemsId
            )
        }.flow.map { pd -> pd.map { episode -> EpisodeItemEntityToDomain.map(episode) } }
    }
}