package com.jmb.moviapp.data.datasource

import com.jmb.moviapp.domain.Movie


interface RemoteDataSource {
    suspend fun getPopularMovies(apiKey: String): List<Movie>
    suspend fun getPopularMoviesPaging(apiKey: String, page: Int): List<Movie>
    suspend fun getSeachMovie(nameMovie: String, page: Int): List<Movie>


}