package com.jmb.moviapp.framework.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jmb.moviapp.data.repositories.MovieRepository
import com.jmb.moviapp.domain.Movie
import com.jmb.moviapp.framework.data.datasource.ServerMovieDataSource
import com.jmb.moviapp.framework.ui.common.Resource
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

    private val _movies = MutableLiveData<Resource<List<Movie>>>()
    val movies: LiveData<Resource<List<Movie>>> get() = _movies

    fun getMovies(apiKey: String) {
        uiScope.launch {
            _movies.value = Resource.Loading()
            try {
                _movies.value = loadPopularMovies.invoke(apiKey)
            } catch (e: Exception) {
                _movies.value = Resource.Failure(e)
            }
        }
    }


}