package com.kelvinievenes.popcorn.domain.usecases.details

import com.kelvinievenes.popcorn.data.repository.MovieRepository
import com.kelvinievenes.popcorn.domain.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DetailsUseCase(private val movieRepository: MovieRepository) {

    suspend fun getMovieDetails(imdbId: String) =
        withContext(Dispatchers.IO) {
            Movie(movieRepository.getMovieDetails(imdbId))
        }

}