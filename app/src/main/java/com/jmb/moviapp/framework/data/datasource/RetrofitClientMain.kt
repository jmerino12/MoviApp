package com.jmb.moviapp.framework.data.datasource

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientMain {

    private var cliente: OkHttpClient

    init {
        val loggin = HttpLoggingInterceptor()
        loggin.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.networkInterceptors().add(loggin)
        cliente = okHttpClientBuilder.build()
    }

    val webServicesMain by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(cliente)
            .build()
            .create(TheMovieDB::class.java)
    }
}

