package com.kavak.brastlewark.data.entities


import com.squareup.moshi.Json

data class Citizen(
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