package com.jmb.moviapp.framework.ui.home

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jmb.moviapp.data.database.RoomDataSource
import com.jmb.moviapp.data.database.getDataBase
import com.jmb.moviapp.data.repositories.MovieRepository
import com.jmb.moviapp.domain.Movie
import com.jmb.moviapp.framework.data.datasource.ServerMovieDataSource
import com.jmb.moviapp.framework.ui.common.Resource
import com.jmb.moviapp.usecases.LoadPopularMovies
import kotlinx.coroutines.*


class HomeViewModel(application: Application) : ViewModel() {
    private val _movies = MutableLiveData<Resource<List<Movie>>>()
    val movies: LiveData<Resource<List<Movie>>> get() = _movies

    private val _firstFecth = MutableLiveData<Boolean>()
    val firstFecth: LiveData<Boolean> get() = _firstFecth

    private val database = getDataBase(application)
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val loadPopularMovies = LoadPopularMovies(
        MovieRepository(
            ServerMovieDataSource(),
            RoomDataSource(database)
        )
    )

    init {
        _firstFecth.value = false
    }

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

    fun getMoviePaging(): LiveData<PagingData<Movie>> {
        return loadPopularMovies.invokePaging("4005b57c0bfee0310d6958d0c8683128")
            .cachedIn(viewModelScope)
    }

    fun isNavigate() {
        _firstFecth.value = true
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
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

