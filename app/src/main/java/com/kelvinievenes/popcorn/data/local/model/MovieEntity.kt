package com.kelvinievenes.popcorn.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kelvinievenes.popcorn.domain.model.Movie
import com.kelvinievenes.popcorn.domain.model.Rating

@Entity(tableName = "movie_entity")
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
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
    val ratings: List<RatingRow>
) {

    constructor(movie: Movie) : this(
        movie.imdbId,
        movie.title,
        movie.year,
        movie.poster,
        movie.rated,
        movie.released,
        movie.genres,
        movie.director,
        movie.writers,
        movie.plot,
        movie.ratings.map { RatingRow(it) }
    )

    fun toMovie(): Movie {
        return Movie(
            imdbId,
            title,
            year,
            poster,
            rated,
            released,
            genres,
            director,
            writers,
            plot,
            ratings.map { Rating(it.source, it.value) }.toList()
        )
    }
}