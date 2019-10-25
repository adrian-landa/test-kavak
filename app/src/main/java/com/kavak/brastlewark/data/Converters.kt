package com.kavak.brastlewark.data

import androidx.room.TypeConverter
import com.kavak.brastlewark.data.entities.Citizen
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class Converters {
    @TypeConverter
    fun fromString(value: String): List<String> {
        val moshi: Moshi = Moshi.Builder().build()
        val listType = Types.newParameterizedType(List::class.java, String::class.java)
        val adapter = moshi.adapter<List<String>>(listType)
        return adapter.fromJson(value) ?: ArrayList()
    }

    @TypeConverter
    fun fromArrayList(list: List<String>): String {
        val moshi: Moshi = Moshi.Builder().build()
        val listType = Types.newParameterizedType(List::class.java, String::class.java)
        val adapter = moshi.adapter<List<String>>(listType)
        return adapter.toJson(list)
    }
}