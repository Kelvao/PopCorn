package com.kelvinievenes.popcorn.domain.usecases.favorites

import com.kelvinievenes.popcorn.data.repository.FavoritesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoritesUseCase(
    private val repository: FavoritesRepository
) {

    private var lastSearch: String = ""

    suspend fun getAllFavorites() =
        withContext(Dispatchers.IO) {
            repository.getAllFavorites()
                .map { it.toMovie() }
                .toMutableList()
        }

    suspend fun getFavorites(search: String) =
        withContext(Dispatchers.IO) {
            if (search.isEmpty() && lastSearch.isEmpty()) {
                return@withContext getAllFavorites()
            } else {
                return@withContext repository
                    .getFavorites(if (search.isEmpty()) lastSearch else search)
                    .map { it.toMovie() }
                    .toMutableList()
            }
        }
}