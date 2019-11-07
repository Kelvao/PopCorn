package com.kelvinievenes.popcorn.domain.model

import com.kelvinievenes.popcorn.data.remote.model.MovieWs

data class Movies(
    var movies: List<Movie>,
    var totalResults: Int
)

data class Movie(
    var title: String,
    var year: String,
    var imdbId: String,
    var poster: String
) {
    constructor(movie: MovieWs) : this(
        movie.title.orEmpty(),
        movie.year.orEmpty(),
        movie.imdbId.orEmpty(),
        movie.poster.orEmpty()
    )

    override fun equals(other: Any?): Boolean {
        return (other is Movie)
                && other.imdbId == imdbId
                && other.poster == poster
                && other.title == title
                && other.year == year
    }
}