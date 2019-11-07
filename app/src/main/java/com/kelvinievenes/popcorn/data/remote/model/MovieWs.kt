package com.kelvinievenes.popcorn.data.remote.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MoviesWs(
    @SerializedName("Search")
    var movies: List<MovieWs>? = emptyList(),
    var totalResults: Int? = 0
): Serializable

data class MovieWs(
    var imdbId: String? = "",
    @SerializedName("Title")
    var title: String? = "",
    @SerializedName("Year")
    var year: String? = "",
    @SerializedName("Poster")
    var poster: String? = ""
): Serializable