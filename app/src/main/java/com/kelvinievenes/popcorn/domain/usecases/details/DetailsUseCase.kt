package com.kelvinievenes.popcorn.domain.usecases.details

import com.kelvinievenes.popcorn.data.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DetailsUseCase ( private val movieRepository: MovieRepository) {

    suspend fun getMovieDetails(imdbId: String) =
        withContext(Dispatchers.IO) {
            val movieDetails = movieRepository.getMovieDetails(imdbId)
        }

}