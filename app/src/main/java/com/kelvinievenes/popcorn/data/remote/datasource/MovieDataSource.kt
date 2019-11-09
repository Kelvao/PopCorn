package com.kelvinievenes.popcorn.data.remote.datasource

import com.kelvinievenes.popcorn.data.remote.OmdbWebService

class MovieDataSource (private val omdbWebService: OmdbWebService) {

    fun getMovieList(search: String, page: Int?) =
        omdbWebService.getMovies(search, page)

    fun getMovieDetails(imdbId: String) =
        omdbWebService.getMovieDetails(imdbId)
}