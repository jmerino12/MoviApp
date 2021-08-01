package com.jmb.moviapp.framework.data.datasource

import com.jmb.moviapp.data.datasource.RemoteDataSource
import com.jmb.moviapp.domain.Movie

class ServerMovieDataSource : RemoteDataSource {

    override suspend fun getPopularMovies(apiKey: String): List<Movie> {
        return RetrofitClientMain.webServicesMain.listPopularMovies(apiKey).results
    }

    override suspend fun getPopularMoviesPaging(apiKey: String, page: Int): List<Movie> {
        return RetrofitClientMain.webServicesMain.listPopularMoviesPaging(apiKey, page).results
    }

    override suspend fun getSeachMovie(nameMovie: String, page: Int): List<Movie> {
        return RetrofitClientMain.webServicesMain.searchMovie(
            "4005b57c0bfee0310d6958d0c8683128",
            page,
            nameMovie
        ).results

    }
}