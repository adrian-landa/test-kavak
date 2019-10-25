package com.kavak.brastlewark.data.entities


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "citizens")
data class Citizen(
    @PrimaryKey
    val id: Int,
    val name: String,
    val thumbnail: String,
    val age: Int,
    val weight: Float,
    val height: Float,
    @field:Json(name = "hair_color")
    val hairColor: String,
    val professions: List<String>,
    val friends: List<String>
)