package com.kelvinievenes.popcorn.data.remote

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kelvinievenes.popcorn.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class WebServiceClient {

    var webService: OmdbWebService

    companion object {
        private const val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
        private const val TIMEOUT_OMDB = 30000L
    }

    init {
        webService = createOmdbWebService(BuildConfig.WS_BASE_URL)
    }

    private fun createOmdbWebService(
        uri: String,
        gson: Gson = getGson(),
        timeout: Long = TIMEOUT_OMDB
    ) = createRetrofitAccess(uri, timeout, Interceptor { chain ->
        var request = chain.request()
        val originalHttpUrl = request.url
        val url = originalHttpUrl.newBuilder().build()
        val builder = request.newBuilder()

        builder.addHeader("Content-Type", "application/json")

        builder.url(url)

        request = builder.build()
        chain.proceed(request)
    }, gson).create(OmdbWebService::class.java)

    private fun createRetrofitAccess(
        uri: String,
        timeout: Long,
        requestInterceptor: Interceptor,
        gson: Gson = getGson()
    ) = Retrofit.Builder().baseUrl(uri).client(
        setupInterceptors(requestInterceptor, timeout).build()
    ).addConverterFactory(
        GsonConverterFactory.create(gson)
    ).build()

    private fun setupInterceptors(
        requestInterceptor: Interceptor,
        timeout: Long
    ) = OkHttpClient.Builder().apply {

        val loggingInterceptor = HttpLoggingInterceptor()

        loggingInterceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.HEADERS
            else HttpLoggingInterceptor.Level.NONE

        addInterceptor(loggingInterceptor)
        addInterceptor(requestInterceptor)
        addNetworkInterceptor(StethoInterceptor())
        connectTimeout(timeout, TimeUnit.SECONDS)
        readTimeout(timeout, TimeUnit.SECONDS)
    }

    private fun getGson() = GsonBuilder().setDateFormat(DATE_FORMAT).create()
}