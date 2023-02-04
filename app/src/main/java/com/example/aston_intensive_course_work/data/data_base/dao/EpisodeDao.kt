package com.example.aston_intensive_course_work.data.data_base.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.aston_intensive_course_work.data.models.data_base_models.EpisodeItemEntity

@Dao
interface EpisodeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(episodes: List<EpisodeItemEntity>)

    @Query(
        "SELECT * FROM EpisodeItemEntity WHERE episodeId IN (:episodesIdList)" +
                "ORDER BY episodeId ASC LIMIT :limit OFFSET :offset"
    )
    suspend fun getAllEpisodesById(
        limit: Int,
        offset: Int,
        episodesIdList: List<Int>
    ): List<EpisodeItemEntity>

    @Query(
        "SELECT * FROM EpisodeItemEntity WHERE (:name IS NULL OR episodeName LIKE '%' || :name || '%')" +
                "AND (:episode IS NULL OR episodeNumber LIKE '%' || :episode || '%')" +
                "ORDER BY episodeId ASC LIMIT :limit OFFSET :offset"
    )
    suspend fun getEpisodesWithFilters(
        limit: Int,
        offset: Int,
        name: String?,
        episode: String?
    ): List<EpisodeItemEntity>

    @Query("DELETE FROM EpisodeItemEntity")
    suspend fun clearAll()
}