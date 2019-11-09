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
    val genre: List<String>,
    val director: String,
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
        if (movie.genre != null && movie.genre.isNotEmpty())
            movie.genre.replace(" ", "").split(",")
        else emptyList(),
        movie.director.orEmpty(),
        movie.plot.orEmpty(),
        movie.ratings.map { Rating(it) }
    )

    override fun equals(other: Any?): Boolean {
        return (other is Movie)
                && other.imdbId == imdbId
                && other.poster == poster
                && other.title == title
                && other.year == year
    }
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