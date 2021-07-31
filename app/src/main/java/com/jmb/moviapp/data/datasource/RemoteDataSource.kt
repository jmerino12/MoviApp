package com.jmb.moviapp.data.datasource

import com.jmb.moviapp.domain.Movie


interface RemoteDataSource {
    suspend fun getPopularMovies(apiKey: String): List<Movie>
}