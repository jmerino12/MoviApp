package com.jmb.moviapp.data.repositories

import com.jmb.moviapp.data.datasource.LocalDataSource
import com.jmb.moviapp.data.datasource.RemoteDataSource
import com.jmb.moviapp.domain.Movie

class MovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {
    suspend fun getPopularMovies(apiKey: String): List<Movie> {

        if (localDataSource.isEmpty()) {
            val movies =
                remoteDataSource.getPopularMovies(apiKey)
            localDataSource.saveMovies(movies)
        }

        return localDataSource.getPopularMovies()
    }
}

