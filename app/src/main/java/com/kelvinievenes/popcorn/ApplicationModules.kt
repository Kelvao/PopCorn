package com.kelvinievenes.popcorn

import androidx.room.Room
import com.kelvinievenes.popcorn.data.local.database.AppDataBase
import com.kelvinievenes.popcorn.data.remote.WebServiceClient
import com.kelvinievenes.popcorn.data.remote.datasource.MovieDataSource
import com.kelvinievenes.popcorn.data.repository.FavoritesRepository
import com.kelvinievenes.popcorn.data.repository.MovieRepository
import com.kelvinievenes.popcorn.domain.usecases.details.DetailsUseCase
import com.kelvinievenes.popcorn.domain.usecases.favorites.FavoritesUseCase
import com.kelvinievenes.popcorn.domain.usecases.movielist.MovieListUseCase
import com.kelvinievenes.popcorn.presentation.details.presenter.DetailsPresenter
import com.kelvinievenes.popcorn.presentation.favorites.presenter.FavoritesPresenter
import com.kelvinievenes.popcorn.presentation.movielist.presenter.MovieListPresenter
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val webServiceModules = module {
    single { WebServiceClient().webService }
}

val databaseModules = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDataBase::class.java,
            BuildConfig.DATABASE_NAME
        ).build()
    } // RoomDataBase
}

val dataSourceModules = module {
    single { MovieDataSource(get()) }
    single { get<AppDataBase>().favoritesLocalDataSource() }
}

val repositoryModules = module {
    single { MovieRepository(get()) }
    single { FavoritesRepository(get()) }
}

val useCaseModules = module {
    single { MovieListUseCase(get()) }
    single { DetailsUseCase(get()) }
    single { FavoritesUseCase(get()) }
}

val presenterModules = module {
    factory { MovieListPresenter(get()) }
    factory { DetailsPresenter(get()) }
    factory { FavoritesPresenter(get()) }
}

var applicationModules = listOf(
    webServiceModules,
    databaseModules,
    dataSourceModules,
    repositoryModules,
    useCaseModules,
    presenterModules
)