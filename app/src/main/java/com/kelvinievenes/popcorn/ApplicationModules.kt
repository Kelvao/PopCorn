package com.kelvinievenes.popcorn

import com.kelvinievenes.popcorn.data.remote.WebServiceClient
import com.kelvinievenes.popcorn.data.remote.datasource.MovieDataSource
import com.kelvinievenes.popcorn.data.repository.MovieRepository
import com.kelvinievenes.popcorn.domain.usecases.details.DetailsUseCase
import com.kelvinievenes.popcorn.domain.usecases.movielist.MovieListUseCase
import com.kelvinievenes.popcorn.presentation.details.presenter.DetailsPresenter
import com.kelvinievenes.popcorn.presentation.details.view.DetailsActivity
import com.kelvinievenes.popcorn.presentation.movielist.presenter.MovieListPresenter
import com.kelvinievenes.popcorn.presentation.movielist.view.MovieListActivity
import org.koin.dsl.module

val webServiceModules = module {
    single { WebServiceClient().webService }
}

val dataSourceModules = module {
    single { MovieDataSource(get()) }
}

val repositoryModules = module {
    single { MovieRepository(get()) }
}

val useCaseModules = module {
    single { MovieListUseCase(get()) }
    single { DetailsUseCase(get()) }
}

val presenterModules = module {
    factory { MovieListPresenter(get()) }
    factory { DetailsPresenter(get()) }
}

val viewModules = module {
    factory { MovieListActivity() }
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