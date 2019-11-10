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
    fun fromGenreRowListToJson(genres: List<String>?): String? = genres.toString()

    @TypeConverter
    fun toGenreList(genres: String?): List<String>? = genres?.split(",")

}