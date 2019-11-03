package com.kelvinievenes.popcorn

import com.kelvinievenes.popcorn.data.remote.WebServiceClient
import org.koin.dsl.module

val webServiceModules = module {
    single { WebServiceClient().webService }
}

var applicationModules = listOf(webServiceModules)