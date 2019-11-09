package com.kelvinievenes.popcorn.domain.usecases.movielist

import com.kelvinievenes.popcorn.data.repository.MovieRepository
import com.kelvinievenes.popcorn.domain.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieListUseCase(private val repository: MovieRepository) {

    private var lastPage = 1
    private var lastSearch = ""

    suspend fun getMovieList(search: String? = null) =
        withContext(Dispatchers.IO) {
            if (!search.isNullOrBlank()) {
                lastSearch = search
                lastPage = 1
            }

            val moviesResponse = repository.getMovieList(lastSearch, lastPage++)
            val movies = moviesResponse.movies?.map { Movie(it) }

            return@withContext movies?.toMutableList() ?: mutableListOf()
        }
}