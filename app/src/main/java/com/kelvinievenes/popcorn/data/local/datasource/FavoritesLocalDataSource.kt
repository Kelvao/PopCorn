package com.kelvinievenes.popcorn.data.local.datasource

import androidx.room.*
import com.kelvinievenes.popcorn.data.local.model.MovieEntity

@Dao
interface FavoritesLocalDataSource {

    @Query(" SELECT * FROM movie_entity")
    fun getAllFavorites(): List<MovieEntity>

    @Query(" SELECT * FROM movie_entity WHERE title LIKE :search")
    fun getFavorites(search: String): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavorite(movie: MovieEntity)

    @Delete
    fun removeFavorite(movie: MovieEntity)

    @Query("SELECT * FROM movie_entity WHERE imdbId = :imdbId")
    fun getFavoriteByImdbId(imdbId: String): MovieEntity?

}