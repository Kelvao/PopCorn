package com.kelvinievenes.popcorn.data.repository

import com.kelvinievenes.popcorn.data.local.database.runDatabaseTransaction
import com.kelvinievenes.popcorn.data.local.datasource.FavoritesLocalDataSource
import com.kelvinievenes.popcorn.data.local.model.MovieEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoritesRepository(
    private val dataSource: FavoritesLocalDataSource
) {
    suspend fun getFavorites() =
        withContext(Dispatchers.IO) {
            runDatabaseTransaction {
                dataSource.getAllFavorites()
            }
        }


    suspend fun addFavorite(movie: MovieEntity) =
        withContext(Dispatchers.IO) {
            runDatabaseTransaction {
                dataSource.addFavorite(movie)
            }
        }

    suspend fun removeFavorite(movieEntity: MovieEntity) =
        withContext(Dispatchers.IO) {
            runDatabaseTransaction {
                dataSource.removeFavorite(movieEntity)
                movieEntity
            }
        }
}