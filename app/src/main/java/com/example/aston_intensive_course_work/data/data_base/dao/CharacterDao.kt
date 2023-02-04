package com.example.aston_intensive_course_work.data.data_base.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.aston_intensive_course_work.data.models.data_base_models.CharacterItemEntity

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<CharacterItemEntity>)

    @Query(
        "SELECT * FROM CharacterItemEntity WHERE characterId IN (:charactersIdList)" +
                "ORDER BY characterId ASC LIMIT :limit OFFSET :offset"
    )
    suspend fun getAllCharactersById(
        limit: Int,
        offset: Int,
        charactersIdList: List<Int>
    ): List<CharacterItemEntity>

    @Query(
        "SELECT * FROM CharacterItemEntity WHERE (:name IS NULL OR characterName LIKE '%' || :name || '%') " +
                "AND (:status IS NULL OR characterStatus LIKE :status) " +
                "AND (:species IS NULL OR characterSpecies LIKE '%' || :species || '%')" +
                "AND (:type IS NULL OR characterType LIKE '%' || :type || '%') " +
                "AND (:gender IS NULL OR characterGender LIKE :gender)" +
                "ORDER BY characterId ASC LIMIT :limit OFFSET :offset"
    )
    suspend fun getCharactersWithFilters(
        limit: Int,
        offset: Int,
        name: String?,
        status: String?,
        species: String?,
        type: String?,
        gender: String?
    ): List<CharacterItemEntity>

    @Query("DELETE FROM CharacterItemEntity")
    suspend fun clearAll()
}