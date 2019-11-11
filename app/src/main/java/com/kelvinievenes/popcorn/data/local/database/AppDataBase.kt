package com.kelvinievenes.popcorn.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kelvinievenes.popcorn.data.local.datasource.FavoritesLocalDataSource
import com.kelvinievenes.popcorn.data.local.model.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {

    abstract fun favoritesLocalDataSource(): FavoritesLocalDataSource

}