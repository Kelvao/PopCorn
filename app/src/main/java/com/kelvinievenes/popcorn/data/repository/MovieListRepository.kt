package com.kelvinievenes.popcorn.data.repository

import com.kelvinievenes.popcorn.data.remote.datasource.MovieListDataSource
import com.kelvinievenes.popcorn.data.remote.model.MoviesWs
import com.kelvinievenes.popcorn.data.repository.base.performRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieListRepository(private val dataSource: MovieListDataSource) {

    suspend fun getMovieList(search: String, page: Int?) =
        withContext(Dispatchers.IO) {
            performRequest(
                dataSource.getMovieList(search, page).execute()
            ) as MoviesWs
        }

}