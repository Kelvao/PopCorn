package com.kelvinievenes.popcorn.domain.model

import com.kelvinievenes.popcorn.data.remote.model.MovieWs
import com.kelvinievenes.popcorn.data.remote.model.RatingWs

data class Movies(
    var movies: List<Movie>,
    var totalResults: Int
)

data class Movie(
    var title: String,
    var year: String,
    var imdbId: String,
    var poster: String,
    val rated: String,
    val released: String,
    val runtime: String,
    val genres: List<String>,
    val director: String,
    val writers: String,
    val plot: String,
    val ratings: List<Rating>
) {
    constructor(movie: MovieWs) : this(
        movie.title.orEmpty(),
        movie.year.orEmpty(),
        movie.imdbId.orEmpty(),
        movie.poster.orEmpty(),
        movie.rated.orEmpty(),
        movie.released.orEmpty(),
        movie.runtime.orEmpty(),
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
){
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