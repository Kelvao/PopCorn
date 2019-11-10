package com.kelvinievenes.popcorn.domain.usecases.favorites

import com.kelvinievenes.popcorn.data.local.model.MovieEntity
import com.kelvinievenes.popcorn.data.repository.FavoritesRepository
import com.kelvinievenes.popcorn.domain.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoritesUseCase(
    private val repository: FavoritesRepository
) {

    suspend fun getFavorites() =
        withContext(Dispatchers.IO) {
            repository.getFavorites().map { it.toMovie() }
        }

    suspend fun removeFavorite(movie: Movie) =
        withContext(Dispatchers.IO) {
            repository.removeFavorite(MovieEntity(movie)).toMovie()
        }

}