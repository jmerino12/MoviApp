package com.jmb.moviapp.framework.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jmb.moviapp.data.repositories.SearchRepo
import com.jmb.moviapp.domain.Movie
import com.jmb.moviapp.framework.data.datasource.ServerMovieDataSource
import com.jmb.moviapp.usecases.SearchMovies
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow


class SearchMovieViewModel : ViewModel() {
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private lateinit var newResult: Flow<PagingData<Movie>>

    private var currentQueryValue: String? = null

    private var currentSearchResult: Flow<PagingData<Movie>>? = null

    private val searchMovie = SearchMovies(
        SearchRepo(ServerMovieDataSource())
    )


    fun searchRepo(queryString: String): Flow<PagingData<Movie>> {
        currentQueryValue = queryString
        val newResult = searchMovie.invokeSeachMovie(queryString)
        return newResult.cachedIn(viewModelScope)
    }


    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SearchMovieViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SearchMovieViewModel() as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel -  error ")
        }
    }
}