package com.example.aston_intensive_course_work.data.paging

import android.net.ConnectivityManager
import androidx.room.withTransaction
import com.example.aston_intensive_course_work.data.client.RetrofitService
import com.example.aston_intensive_course_work.data.data_base.CacheDataBase
import com.example.aston_intensive_course_work.data.extensions.isStorageDurationExpired
import com.example.aston_intensive_course_work.data.extensions.setUpdateTimeIfNeeded
import com.example.aston_intensive_course_work.data.mapers.iternals.CharacterItemResponseToEntity
import com.example.aston_intensive_course_work.data.models.data_base_models.CharacterItemEntity

class GetCharactersPagingSource(
    private val retrofitService: RetrofitService,
    connectivityManager: ConnectivityManager,
    cache: CacheDataBase,
    private val name: String,
    private val status: String,
    private val species: String,
    private val type: String,
    private val gender: String
) : BaseUnitPagingSource<CharacterItemEntity>(
    cache = cache,
    connectivityManager = connectivityManager
) {

    override suspend fun getItemsRemote(params: LoadParams<Int>): LoadResult<Int, CharacterItemEntity> {
        val pageNumber = params.key ?: 1

        val response = retrofitService.getCharactersWithFilters(
            page = pageNumber,
            name = name,
            status = status,
            species = species,
            type = type,
            gender = gender
        )

        val responseCode = response.code()
        val responseBody = response.body()

        if (responseCode == SUCCESS_CODE && responseBody != null) {
            val characters = responseBody.results.map {
                CharacterItemResponseToEntity.map(
                    it
                )
            }

            cache.withTransaction {
                if (cache.isStorageDurationExpired()) refreshCache()
                characterDao.insertAll(characters.also { cache.setUpdateTimeIfNeeded() })
            }

            return LoadResult.Page(
                data = characters,
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
    ): LoadResult<Int, CharacterItemEntity> {
        val pageNumber = params.key ?: 1

        var characters = emptyList<CharacterItemEntity>()

        cache.withTransaction {
            characters = characterDao.getCharactersWithFilters(
                limit = params.loadSize,
                offset = (pageNumber - 1) * params.loadSize,
                name = name.ifEmpty { null },
                status = status.ifEmpty { null },
                species = species.ifEmpty { null },
                type = type.ifEmpty { null },
                gender = gender.ifEmpty { null }
            )
        }

        return LoadResult.Page(
            data = characters,
            prevKey = if (pageNumber == 1) null else pageNumber,
            nextKey = if (characters.size < params.loadSize) null else pageNumber + 1
        )
    }

}