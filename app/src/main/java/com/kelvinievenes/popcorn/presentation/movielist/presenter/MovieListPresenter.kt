package com.kelvinievenes.popcorn.presentation.movielist.presenter

import com.kelvinievenes.popcorn.mechanism.coroutine.ExecutorCoroutineScope
import com.kelvinievenes.popcorn.mechanism.coroutine.getCoroutineScope
import com.kelvinievenes.popcorn.domain.model.Movie
import com.kelvinievenes.popcorn.domain.usecases.movielist.MovieListUseCase
import com.kelvinievenes.popcorn.mechanism.livedata.MutableLiveDataResource
import com.kelvinievenes.popcorn.mechanism.livedata.Resource

class MovieListPresenter(
    private val useCase: MovieListUseCase
) : ExecutorCoroutineScope by getCoroutineScope() {

    val moviesLiveData = MutableLiveDataResource<MutableList<Movie>>()
    private var blockNextGetMore = false

    fun getMovieList(search: String) = runCoroutine {
        moviesLiveData.postValue(Resource.loading())
        val moviesList = useCase.getMovieList(search)
        if (moviesList.isEmpty()) {
            blockNextGetMore = true
            moviesLiveData.postValue(Resource.empty())
        } else {
            moviesLiveData.postValue(Resource.success(moviesList))
        }
    } onError {
        moviesLiveData.postValue(Resource.error())
    }

    fun getMoreMovies() = runCoroutine {
        if (blockNextGetMore) cancelJobs()

        val moviesList = useCase.getMovieList()
        if (moviesList.isEmpty()) {
            moviesLiveData.postValue(Resource.emptyNextPage())
        } else {
            moviesLiveData.postValue(Resource.successNextPage(moviesList))
        }
    } onError {
        moviesLiveData.postValue(Resource.errorNextPage())
    }
}