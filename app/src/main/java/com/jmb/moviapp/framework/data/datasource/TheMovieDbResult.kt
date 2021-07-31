package com.jmb.moviapp.framework.data.datasource


import com.google.gson.annotations.SerializedName
import com.jmb.moviapp.domain.Movie

data class TheMovieDbResult(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)

