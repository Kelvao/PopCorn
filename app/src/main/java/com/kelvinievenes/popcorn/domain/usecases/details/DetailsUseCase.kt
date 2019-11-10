package com.kelvinievenes.popcorn.domain.usecases.details

import com.kelvinievenes.popcorn.data.local.model.MovieEntity
import com.kelvinievenes.popcorn.data.repository.FavoritesRepository
import com.kelvinievenes.popcorn.data.repository.MovieRepository
import com.kelvinievenes.popcorn.domain.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DetailsUseCase(
    private val movieRepository: MovieRepository,
    private val favoritesRepository: FavoritesRepository
) {

    private lateinit var movieCached: Movie

    suspend fun getMovieDetails(imdbId: String) =
        withContext(Dispatchers.IO) {
            movieCached = Movie(movieRepository.getMovieDetails(imdbId))
            movieCached
        }

    suspend fun addFavorite() =
        withContext(Dispatchers.IO) {
            favoritesRepository.addFavorite(MovieEntity(movieCached))
        }

    suspend fun removeFavorite() =
        withContext(Dispatchers.IO) {
            favoritesRepository.removeFavorite(MovieEntity(movieCached)).toMovie()
        }
}