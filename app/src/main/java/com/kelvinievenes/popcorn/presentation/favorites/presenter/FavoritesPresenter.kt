package com.kelvinievenes.popcorn.presentation.favorites.presenter

import com.kelvinievenes.popcorn.domain.usecases.favorites.FavoritesUseCase
import com.kelvinievenes.popcorn.mechanism.coroutine.ExecutorCoroutineScope
import com.kelvinievenes.popcorn.mechanism.coroutine.getCoroutineScope

class FavoritesPresenter(
    private val useCase: FavoritesUseCase
) : ExecutorCoroutineScope by getCoroutineScope() {

}