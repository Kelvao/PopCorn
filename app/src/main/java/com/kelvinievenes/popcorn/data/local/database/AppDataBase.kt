package com.kelvinievenes.popcorn.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kelvinievenes.popcorn.data.local.datasource.FavoritesLocalDataSource
import com.kelvinievenes.popcorn.data.local.model.MovieEntity
import com.kelvinievenes.popcorn.data.repository.base.ErrorCode
import com.kelvinievenes.popcorn.data.repository.base.PopCornException

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {

    abstract fun favoritesLocalDataSource(): FavoritesLocalDataSource

}

suspend fun <T : Any?> runDatabaseTransaction(run: suspend () -> T): T {
    try {
        return run()
    } catch (e: Exception) {
        when (e) {
            is PopCornException -> throw PopCornException(e.errorCode)
            else -> throw PopCornException(ErrorCode.GENERIC_ERROR)
        }
    }
}