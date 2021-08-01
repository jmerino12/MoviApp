package com.jmb.moviapp.usecases


import com.jmb.moviapp.data.repositories.SearchRepo


class SearchMovies(private val searchRepository: SearchRepo) {

    fun invokeSeachMovie(string: String) = searchRepository.getMovie2(string)
}