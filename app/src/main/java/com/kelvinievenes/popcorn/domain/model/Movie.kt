package com.kelvinievenes.popcorn.domain.model

import com.kelvinievenes.popcorn.data.local.model.RatingRow
import com.kelvinievenes.popcorn.data.remote.model.MovieWs
import com.kelvinievenes.popcorn.data.remote.model.RatingWs

data class Movie(
    var imdbId: String,
    var title: String,
    var year: String,
    var poster: String,
    val rated: String,
    val released: String,
    val genres: List<String>,
    val director: String,
    val writers: String,
    val plot: String,
    val ratings: List<Rating>
) {
    constructor(movie: MovieWs) : this(
        movie.imdbId.orEmpty(),
        movie.title.orEmpty(),
        movie.year.orEmpty(),
        movie.poster.orEmpty(),
        movie.rated.orEmpty(),
        movie.released.orEmpty(),
        if (movie.genres != null && movie.genres.isNotEmpty())
            movie.genres.replace(" ", "").split(",")
        else emptyList(),
        movie.director.orEmpty(),
        movie.writers.orEmpty(),
        movie.plot.orEmpty(),
        if (movie.ratings != null && movie.ratings.isNotEmpty())
            movie.ratings.map { Rating(it) }
        else emptyList()
    )

    fun getPoster(quality: Quality) = poster.replace(Quality.NORMAL.quality, quality.quality)
}

enum class Quality(
    val quality: String
) {
    NORMAL("SX300"),
    HIGH("SX1200")
}

data class Rating(
    val source: String,
    val value: String
) {
    constructor(rating: RatingWs) : this(
        rating.source.orEmpty(),
        rating.value.orEmpty()
    )
}