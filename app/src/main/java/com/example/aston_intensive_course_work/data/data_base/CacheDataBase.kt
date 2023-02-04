package com.example.aston_intensive_course_work.data.data_base

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.aston_intensive_course_work.data.data_base.converter.ArrayConverter
import com.example.aston_intensive_course_work.data.data_base.converter.LocationSimpleItemConverter
import com.example.aston_intensive_course_work.data.data_base.dao.CharacterDao
import com.example.aston_intensive_course_work.data.data_base.dao.EpisodeDao
import com.example.aston_intensive_course_work.data.data_base.dao.LocationDao
import com.example.aston_intensive_course_work.data.models.data_base_models.CharacterItemEntity
import com.example.aston_intensive_course_work.data.models.data_base_models.EpisodeItemEntity
import com.example.aston_intensive_course_work.data.models.data_base_models.LocationItemEntity

const val STORAGE_DURATION = 86_400_000L

@Database(
    entities = [CharacterItemEntity::class, LocationItemEntity::class, EpisodeItemEntity::class],
    version = 1
)
@TypeConverters(ArrayConverter::class, LocationSimpleItemConverter::class)
abstract class CacheDataBase : RoomDatabase() {

    private var _lastTimeUpdated: Long? = null

    abstract fun characterDao(): CharacterDao
    abstract fun locationDao(): LocationDao
    abstract fun episodeDao(): EpisodeDao


    fun setUpdateTime(updateTime: Long?) {
        _lastTimeUpdated = updateTime
    }

    val lastTimeUpdated: Long? get() = _lastTimeUpdated
}