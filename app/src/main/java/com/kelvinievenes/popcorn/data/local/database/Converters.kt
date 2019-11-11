package com.kelvinievenes.popcorn.data.local.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kelvinievenes.popcorn.data.local.model.RatingRow

class Converters {

    @TypeConverter
    fun fromRatingRowListToJson(ratings: List<RatingRow>?): String? = Gson().toJson(ratings)

    @TypeConverter
    fun toRatingList(json: String?): List<RatingRow>? {
        val notesType = object : TypeToken<List<RatingRow>>() {}.type
        return Gson().fromJson<List<RatingRow>>(json, notesType)
    }

    @TypeConverter
    fun fromGenreRowListToJson(genres: List<String>?): String? = Gson().toJson(genres)

    @TypeConverter
    fun toGenreList(json: String?): List<String>? {
        val notesType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson<List<String>>(json, notesType)
    }
}