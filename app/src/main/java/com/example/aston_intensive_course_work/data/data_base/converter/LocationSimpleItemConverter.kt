package com.example.aston_intensive_course_work.data.data_base.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.aston_intensive_course_work.data.extensions.convertFromJson
import com.example.aston_intensive_course_work.data.models.data_base_models.LocationSimpleItemEntity
import com.google.gson.Gson

@ProvidedTypeConverter
class LocationSimpleItemConverter {

    @TypeConverter
    fun fromLocationSimpleItem(value: LocationSimpleItemEntity): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toLocationSimpleItem(value: String): LocationSimpleItemEntity {
        return try {
            Gson().convertFromJson(value)
        } catch (e: Exception) {
            LocationSimpleItemEntity(locationSimpleName = "", locationSimpleUrl = "")
        }
    }
}