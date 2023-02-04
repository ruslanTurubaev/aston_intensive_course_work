package com.example.aston_intensive_course_work.data.data_base.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.aston_intensive_course_work.data.models.data_base_models.LocationItemEntity

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(locations: List<LocationItemEntity>)

    @Query("SELECT * FROM LocationItemEntity WHERE locationId = :locationId")
    suspend fun getLocationById(
        locationId: Int
    ): LocationItemEntity

    @Query(
        "SELECT * FROM LocationItemEntity WHERE (:name IS NULL OR locationName LIKE '%' || :name || '%')" +
                "AND (:type IS NULL OR locationType LIKE '%' || :type || '%') " +
                "AND (:dimension IS NULL OR locationDimension LIKE '%' || :dimension || '%')" +
                "ORDER BY locationId ASC LIMIT :limit OFFSET :offset"
    )
    suspend fun getLocationsWithFilters(
        limit: Int,
        offset: Int,
        name: String?,
        type: String?,
        dimension: String?
    ): List<LocationItemEntity>

    @Query("DELETE FROM LocationItemEntity")
    suspend fun clearAll()
}