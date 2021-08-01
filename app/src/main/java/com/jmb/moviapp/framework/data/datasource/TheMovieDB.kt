package com.jmb.moviapp.framework.data.datasource

import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieDB {
    @GET("movie/popular")
    suspend fun listPopularMovies(@Query("api_key") apiKey: String): TheMovieDbResult

    @GET("movie/popular")
    suspend fun listPopularMoviesPaging(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): TheMovieDbResult

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
        @Query("query") string: String
    ): TheMovieDbResult
}