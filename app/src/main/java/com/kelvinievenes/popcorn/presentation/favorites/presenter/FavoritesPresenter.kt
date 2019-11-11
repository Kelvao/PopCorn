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

    val favoritesLiveData = MutableLiveDataResource<MutableList<Movie>>()

    fun getAllFavorites() = runCoroutine {
        favoritesLiveData.postValue(Resource.loading())
        val favorites = useCase.getAllFavorites()
        if (favorites.size > 0) {
            favoritesLiveData.postValue(Resource.success(useCase.getAllFavorites()))
        } else {
            favoritesLiveData.postValue(Resource.empty())
        }
    } onError {
        favoritesLiveData.postValue(Resource.error())
    }

    fun getFavorites(search: String = "") = runCoroutine {
        favoritesLiveData.postValue(Resource.loading())
        val favorites = useCase.getFavorites(search)
        if (favorites.size > 0) {
            favoritesLiveData.postValue(Resource.success(useCase.getFavorites(search)))
        } else {
            favoritesLiveData.postValue(
                if (search.isEmpty()) Resource.empty()
                else Resource.emptySearch()
            )
        }
    } onError {
        favoritesLiveData.postValue(Resource.error())
    }


}