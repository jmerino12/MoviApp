package com.jmb.moviapp.framework.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jmb.moviapp.data.repositories.MovieRepository
import com.jmb.moviapp.domain.Movie
import com.jmb.moviapp.framework.data.datasource.ServerMovieDataSource
import com.jmb.moviapp.usecases.LoadPopularMovies
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class HomeViewModel : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val loadPopularMovies = LoadPopularMovies(
        MovieRepository(
            ServerMovieDataSource()
        )
    )

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    fun getMovies(apiKey: String) {
        uiScope.launch {
            _movies.value = loadPopularMovies.invoke(apiKey)
        }
    }


}