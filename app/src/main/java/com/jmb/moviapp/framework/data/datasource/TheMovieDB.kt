package com.jmb.moviapp.framework.data.datasource

import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieDB {
    @GET("movie/popular")
    suspend fun listPopularMovies(@Query("api_key") apiKey: String): TheMovieDbResult
}