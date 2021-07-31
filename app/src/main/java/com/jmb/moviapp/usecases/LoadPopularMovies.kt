package com.jmb.moviapp.usecases

import com.jmb.moviapp.data.repositories.MovieRepository
import com.jmb.moviapp.domain.Movie
import com.jmb.moviapp.framework.ui.common.Resource

class LoadPopularMovies(private val movieRepository: MovieRepository) {
    suspend fun invoke(apiKey: String): Resource<List<Movie>> =
        Resource.Success(movieRepository.getPopularMovies(apiKey))

}