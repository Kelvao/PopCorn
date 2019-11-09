package com.kelvinievenes.popcorn.presentation.details.presenter

import com.kelvinievenes.popcorn.domain.model.Movie
import com.kelvinievenes.popcorn.domain.usecases.details.DetailsUseCase
import com.kelvinievenes.popcorn.mechanism.coroutine.ExecutorCoroutineScope
import com.kelvinievenes.popcorn.mechanism.coroutine.getCoroutineScope
import com.kelvinievenes.popcorn.mechanism.livedata.MutableLiveDataResource
import com.kelvinievenes.popcorn.mechanism.livedata.Resource

class DetailsPresenter(private val detailsUseCase: DetailsUseCase) :
    ExecutorCoroutineScope by getCoroutineScope() {

    val movieDetailsLiveData = MutableLiveDataResource<Movie>()

    fun getMovieDetails(imdbId: String) = runCoroutine {
        movieDetailsLiveData.postValue(Resource.loading())
        detailsUseCase.getMovieDetails(imdbId).let {
            movieDetailsLiveData.postValue(Resource.success(it))
        }
    } onError {
        movieDetailsLiveData.postValue(Resource.error())
    }

}