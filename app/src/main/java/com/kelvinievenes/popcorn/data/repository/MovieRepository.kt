package com.kelvinievenes.popcorn.data.repository

import com.kelvinievenes.popcorn.data.remote.datasource.MovieDataSource
import com.kelvinievenes.popcorn.data.remote.model.MoviesWs
import com.kelvinievenes.popcorn.data.repository.base.performRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(private val dataSource: MovieDataSource) {

    suspend fun getMovieList(search: String, page: Int?) =
        withContext(Dispatchers.IO) {
            performRequest(
                dataSource.getMovieList(search, page).execute()
            ) as MoviesWs
        }

    suspend fun getMovieDetails(imdbId: String) =
        withContext(Dispatchers.IO) {
            performRequest(
                dataSource.getMovieDetails(imdbId).execute()
            )
        }

}