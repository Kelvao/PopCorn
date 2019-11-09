package com.kelvinievenes.popcorn.data.remote.model

data class MovieDetailWs (
    val title: String? = "",
    val year: String? = "",
    val rated: String? = "",
    val released: String? = "",
    val runtime: String? = "",
    val genre: String? = "",
    val director: String? = "",
    val plot: String? = "",
    val poster: String? = "",
    val ratings: List<RatingsWs> = emptyList()
)

data class RatingsWs(
    val source: String? = "",
    val value: String? = ""
)