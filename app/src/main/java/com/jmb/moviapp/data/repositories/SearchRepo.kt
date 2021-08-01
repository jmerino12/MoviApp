package com.jmb.moviapp.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jmb.moviapp.data.datasource.RemoteDataSource
import com.jmb.moviapp.data.datasource.SearchPagingSource
import com.jmb.moviapp.domain.Movie
import kotlinx.coroutines.flow.Flow

class SearchRepo(private val remoteDataSource: RemoteDataSource) {

    fun getMovie2(query: String): Flow<PagingData<Movie>> =
        Pager(
            config = PagingConfig(
                pageSize = 500,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { SearchPagingSource(remoteDataSource, query) },
        ).flow
}