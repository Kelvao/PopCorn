package com.kelvinievenes.popcorn

import com.kelvinievenes.popcorn.data.local.FavoritesDataSource
import com.kelvinievenes.popcorn.data.remote.WebServiceClient
import com.kelvinievenes.popcorn.data.remote.datasource.MovieDataSource
import com.kelvinievenes.popcorn.data.repository.FavoritesRepository
import com.kelvinievenes.popcorn.data.repository.MovieRepository
import com.kelvinievenes.popcorn.domain.usecases.details.DetailsUseCase
import com.kelvinievenes.popcorn.domain.usecases.favorites.FavoritesUseCase
import com.kelvinievenes.popcorn.domain.usecases.movielist.MovieListUseCase
import com.kelvinievenes.popcorn.presentation.details.presenter.DetailsPresenter
import com.kelvinievenes.popcorn.presentation.details.view.DetailsActivity
import com.kelvinievenes.popcorn.presentation.favorites.presenter.FavoritesPresenter
import com.kelvinievenes.popcorn.presentation.main.view.MainActivity
import com.kelvinievenes.popcorn.presentation.movielist.presenter.MovieListPresenter
import org.koin.dsl.module

val webServiceModules = module {
    single { WebServiceClient().webService }
}

val dataSourceModules = module {
    single { MovieDataSource(get()) }
    single { FavoritesDataSource() }
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

val viewModules = module {
    factory { MainActivity() }
    factory { DetailsActivity() }
}

var applicationModules = listOf(
    webServiceModules,
    dataSourceModules,
    repositoryModules,
    useCaseModules,
    presenterModules,
    viewModules
)