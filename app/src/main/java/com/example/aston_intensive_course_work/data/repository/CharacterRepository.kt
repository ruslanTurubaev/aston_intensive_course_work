package com.example.aston_intensive_course_work.data.repository

import android.net.ConnectivityManager
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.aston_intensive_course_work.data.client.RetrofitService
import com.example.aston_intensive_course_work.data.data_base.CacheDataBase
import com.example.aston_intensive_course_work.data.mapers.external.CharacterItemEntityToDomain
import com.example.aston_intensive_course_work.data.paging.GetCharactersByIdPagingSource
import com.example.aston_intensive_course_work.data.paging.GetCharactersPagingSource
import com.example.aston_intensive_course_work.domain.interfaces.CharacterRepositoryInterface
import com.example.aston_intensive_course_work.domain.models.items_models.CharacterItemDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CharacterRepository(
    private val retrofit: RetrofitService,
    private val cache: CacheDataBase,
    private val connectivityManager: ConnectivityManager
) : CharacterRepositoryInterface {

    override fun getCharacters(
        name: String,
        status: String,
        species: String,
        type: String,
        gender: String
    ): Flow<PagingData<CharacterItemDomain>> {

        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 10,
                enablePlaceholders = false,
                initialLoadSize = 20
            )
        ) {
            GetCharactersPagingSource(
                retrofitService = retrofit,
                connectivityManager = connectivityManager,
                cache = cache,
                name = name,
                status = status,
                species = species,
                type = type,
                gender = gender
            )
        }.flow.map { pd ->
            pd.map { character ->
                CharacterItemEntityToDomain.map(
                    character
                )
            }
        }
    }

    override fun getAllCharacterById(itemsId: List<Int>): Flow<PagingData<CharacterItemDomain>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 5,
                enablePlaceholders = false,
                initialLoadSize = 10
            )
        ) {
            GetCharactersByIdPagingSource(
                retrofitService = retrofit,
                connectivityManager = connectivityManager,
                cache = cache,
                itemsId = itemsId
            )
        }.flow.map { pd ->
            pd.map { character ->
                CharacterItemEntityToDomain.map(
                    character
                )
            }
        }
    }
}