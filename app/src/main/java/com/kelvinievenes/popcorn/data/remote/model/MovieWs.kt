package com.kelvinievenes.popcorn.data.remote.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MoviesWs(
    @SerializedName("Search")
    var movies: List<MovieWs>? = emptyList(),
    var totalResults: Int? = 0
): Serializable

data class MovieWs(
    @SerializedName("imdbID")
    var imdbId: String? = "",
    @SerializedName("Title")
    var title: String? = "",
    @SerializedName("Year")
    var year: String? = "",
    @SerializedName("Poster")
    var poster: String? = "",
    @SerializedName("Rated")
    val rated: String? = "",
    @SerializedName("Released")
    val released: String? = "",
    @SerializedName("Genre")
    val genres: String? = "",
    @SerializedName("Director")
    val director: String? = "",
    @SerializedName("Writer")
    val writers: String? = "",
    @SerializedName("Plot")
    val plot: String? = "",
    @SerializedName("Ratings")
    val ratings: List<RatingWs>? = emptyList()
): Serializable

data class RatingWs(
    @SerializedName("Source")
    val source: String? = "",
    @SerializedName("Value")
    val value: String? = ""
): Serializable