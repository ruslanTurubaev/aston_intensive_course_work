package com.example.aston_intensive_course_work.data.data_base.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.aston_intensive_course_work.data.extensions.convertFromJson
import com.google.gson.Gson

@ProvidedTypeConverter
class ArrayConverter {

    @TypeConverter
    fun fromStringArrayList(value: List<String>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringArrayList(value: String): List<String> {
        return try {
            Gson().convertFromJson<ArrayList<String>>(value)
        } catch (e: Exception) {
            arrayListOf()
        }
    }
}