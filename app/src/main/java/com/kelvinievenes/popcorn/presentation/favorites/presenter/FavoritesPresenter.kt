package com.kelvinievenes.popcorn.presentation.favorites.presenter

import com.kelvinievenes.popcorn.domain.model.Movie
import com.kelvinievenes.popcorn.domain.usecases.favorites.FavoritesUseCase
import com.kelvinievenes.popcorn.mechanism.coroutine.ExecutorCoroutineScope
import com.kelvinievenes.popcorn.mechanism.coroutine.getCoroutineScope
import com.kelvinievenes.popcorn.mechanism.livedata.MutableLiveDataResource
import com.kelvinievenes.popcorn.mechanism.livedata.Resource

class FavoritesPresenter(
    private val useCase: FavoritesUseCase
) : ExecutorCoroutineScope by getCoroutineScope() {

    val favoritesLiveData = MutableLiveDataResource<List<Movie>>()

    fun getFavorites() = runCoroutine {
        favoritesLiveData.postValue(Resource.loading())
        favoritesLiveData.postValue(Resource.success(useCase.getFavorites()))
    } onError {
        favoritesLiveData.postValue(Resource.error())
    }

    fun removeFavorite(movie: Movie) = runCoroutine {
        favoritesLiveData.postValue(
            Resource.removeSuccess(
                listOf(useCase.removeFavorite(movie))
            )
        )
    } onError {
        favoritesLiveData.postValue(Resource.error())
    }

}