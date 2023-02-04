package com.example.aston_intensive_course_work.data.client

import com.example.aston_intensive_course_work.data.models.network_models.ApiResponse
import com.example.aston_intensive_course_work.data.models.network_models.CharacterItemResponse
import com.example.aston_intensive_course_work.data.models.network_models.EpisodeItemResponse
import com.example.aston_intensive_course_work.data.models.network_models.LocationItemResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    @GET("character/{list}")
    suspend fun getAllCharactersById(@Path("list") charactersIdList: List<Int>): Response<List<CharacterItemResponse>>

    @GET("episode/{list}")
    suspend fun getAllEpisodesById(@Path("list") episodesIdList: List<Int>): Response<List<EpisodeItemResponse>>

    @GET("location/{id}")
    suspend fun getLocationById(@Path("id") locationId: Int): LocationItemResponse

    @GET("character")
    suspend fun getCharactersWithFilters(
        @Query("page") page: Int,
        @Query("name") name: String,
        @Query("status") status: String,
        @Query("species") species: String,
        @Query("type") type: String,
        @Query("gender") gender: String
    ): Response<ApiResponse<CharacterItemResponse>>

    @GET("location")
    suspend fun getLocationsWithFilters(
        @Query("page") page: Int,
        @Query("name") name: String,
        @Query("type") type: String,
        @Query("dimension") dimension: String
    ): Response<ApiResponse<LocationItemResponse>>

    @GET("episode")
    suspend fun getEpisodesWithFilters(
        @Query("page") page: Int,
        @Query("name") name: String,
        @Query("episode") episode: String
    ): Response<ApiResponse<EpisodeItemResponse>>
}