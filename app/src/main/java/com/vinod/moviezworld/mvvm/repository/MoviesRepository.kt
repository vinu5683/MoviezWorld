package com.vinod.moviezworld.mvvm.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.vinod.moviezworld.db.MoviesDatabase
import com.vinod.moviezworld.mvvm.apiserviceproviders.MoviesApiService
import com.vinod.moviezworld.pagination.MovieRemoteMediator
import com.vinod.moviezworld.pagination.MoviesPagingSource
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val moviesApiService: MoviesApiService, private val moviesDB: MoviesDatabase
) {

//    suspend fun getAllMoviesDetails(): MovieResponse {
//        return moviesApiService.getAllMovies()
//    }

    @OptIn(ExperimentalPagingApi::class)
    fun getMoviesList(searchWord: String, type: String = "", year: String) =
        Pager(config = PagingConfig(pageSize = 30, maxSize = 90),
            remoteMediator = MovieRemoteMediator(
                moviesApiService,
                moviesDB,
                searchWord,
                type,
                year
            ),
            pagingSourceFactory = { moviesDB.moviesDao().getMovies() }).liveData
//        pagingSourceFactory = {
//            MoviesPagingSource(
//                moviesApiService,
//                searchWord,
//                type,
//                year
//            )
//        }).liveData

}