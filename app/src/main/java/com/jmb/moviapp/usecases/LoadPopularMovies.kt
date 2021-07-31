package com.jmb.moviapp.usecases

import com.jmb.moviapp.data.repositories.MovieRepository
import com.jmb.moviapp.domain.Movie

class LoadPopularMovies(private val movieRepository: MovieRepository) {
    suspend fun invoke(apiKey: String): List<Movie> = movieRepository.getPopularMovies(apiKey)

}