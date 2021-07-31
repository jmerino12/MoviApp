package com.jmb.moviapp.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.liveData
import com.bumptech.glide.load.HttpException
import com.jmb.moviapp.App
import com.jmb.moviapp.data.datasource.LocalDataSource
import com.jmb.moviapp.data.datasource.RemoteDataSource
import com.jmb.moviapp.domain.Movie
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class MovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : PagingSource<Int, Movie>() {

    private lateinit var movies: List<Movie>
    private val myApp: App = App()
    suspend fun getPopularMovies(apiKey: String): List<Movie> {
        if (localDataSource.isEmpty()) {
            val movies =
                remoteDataSource.getPopularMovies(apiKey)
            localDataSource.saveMovies(movies)
        }
        return localDataSource.getPopularMovies()
    }


    fun getMovie() = Pager(
        config = PagingConfig(
            pageSize = 1000,
            enablePlaceholders = true,
        ),
        pagingSourceFactory = { MovieRepository(remoteDataSource, localDataSource) }
    ).liveData

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            if (myApp.isConnected()) {
                movies = remoteDataSource.getPopularMoviesPaging(
                    "4005b57c0bfee0310d6958d0c8683128",
                    position
                )
                localDataSource.saveMovies(movies)
            } else {
                movies = localDataSource.getPopularMovies()
            }
            LoadResult.Page(
                data = movies,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (movies.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

}

