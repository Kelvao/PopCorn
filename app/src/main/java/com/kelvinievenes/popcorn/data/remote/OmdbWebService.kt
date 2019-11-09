package com.kelvinievenes.popcorn.data.remote

import com.kelvinievenes.popcorn.BuildConfig
import com.kelvinievenes.popcorn.data.remote.model.MovieWs
import com.kelvinievenes.popcorn.data.remote.model.MoviesWs
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbWebService {

    @GET(".")
    fun getMovies(
        @Query("s") search: String,
        @Query("page") page: Int? = 1,
        @Query("type") type: String = TYPE,
        @Query("apiKey") apiKey: String = API_SECRET
    ): Call<MoviesWs>

    @GET(".")
    fun getMovieDetails(
        @Query("i") imdbId: String,
        @Query("type") type: String = TYPE,
        @Query("plot") plot: String = PLOT,
        @Query("apiKey") apiKey: String = API_SECRET
    ): Call<MovieWs>

    companion object {
        private const val TYPE = "movie"
        private const val PLOT = "full"
        private const val API_SECRET = BuildConfig.WS_API_KEY
    }

}