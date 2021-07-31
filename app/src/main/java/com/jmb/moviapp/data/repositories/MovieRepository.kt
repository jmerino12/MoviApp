package com.jmb.moviapp.data.repositories

import com.jmb.moviapp.data.datasource.RemoteDataSource

class MovieRepository(private val remoteDataSource: RemoteDataSource) {
    suspend fun getPopularMovies(apiKey: String) = remoteDataSource.getPopularMovies(apiKey)
}

