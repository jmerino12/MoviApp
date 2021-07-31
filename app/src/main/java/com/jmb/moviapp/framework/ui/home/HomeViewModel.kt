package com.jmb.moviapp.framework.ui.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jmb.moviapp.data.database.RoomDataSource
import com.jmb.moviapp.data.database.getDataBase
import com.jmb.moviapp.data.repositories.MovieRepository
import com.jmb.moviapp.domain.Movie
import com.jmb.moviapp.framework.data.datasource.ServerMovieDataSource
import com.jmb.moviapp.framework.ui.common.Resource
import com.jmb.moviapp.usecases.LoadPopularMovies
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class HomeViewModel(application: Application) : ViewModel() {
    private val database = getDataBase(application)
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val loadPopularMovies = LoadPopularMovies(
        MovieRepository(
            ServerMovieDataSource(),
            RoomDataSource(database)
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

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(application) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel -  error ")
        }
    }


}

