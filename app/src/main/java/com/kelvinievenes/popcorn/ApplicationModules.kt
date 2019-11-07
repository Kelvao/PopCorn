package com.kelvinievenes.popcorn

import com.kelvinievenes.popcorn.data.remote.WebServiceClient
import com.kelvinievenes.popcorn.data.remote.datasource.MovieListDataSource
import com.kelvinievenes.popcorn.data.repository.MovieListRepository
import com.kelvinievenes.popcorn.domain.usecases.movielist.MovieListUseCase
import com.kelvinievenes.popcorn.presentation.movielist.presenter.MovieListPresenter
import com.kelvinievenes.popcorn.presentation.movielist.view.MovieListActivity
import org.koin.dsl.module

val webServiceModules = module {
    single { WebServiceClient().webService }
}

val dataSourceModules = module {
    single { MovieListDataSource(get()) }
}

val repositoryModules = module {
    single { MovieListRepository(get()) }
}

val useCaseModules = module {
    single { MovieListUseCase(get()) }
}

val presenterModules = module {
    factory { MovieListPresenter(get()) }
}

val viewModules = module {
    factory { MovieListActivity() }
}

var applicationModules = listOf(
    webServiceModules,
    dataSourceModules,
    repositoryModules,
    useCaseModules,
    presenterModules,
    viewModules
)