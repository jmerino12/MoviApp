package com.jmb.moviapp.framework.data.datasource

import com.jmb.moviapp.data.datasource.RemoteDataSource
import com.jmb.moviapp.domain.Movie

class ServerMovieDataSource : RemoteDataSource {

    override suspend fun getPopularMovies(apiKey: String): List<Movie> {
        return RetrofitClientMain.webServicesMain.listPopularMovies(apiKey).results
    }
}