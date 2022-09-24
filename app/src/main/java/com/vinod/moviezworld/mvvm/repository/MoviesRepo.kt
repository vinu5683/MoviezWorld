package com.vinod.moviezworld.mvvm.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.vinod.moviezworld.mvvm.apiserviceproviders.MoviesApiService
import com.vinod.moviezworld.mvvm.models.MovieResponse
import com.vinod.moviezworld.mvvm.models.MoviesResponseModel
import com.vinod.moviezworld.pagination.MoviesPagingSource
import javax.inject.Inject

class MoviesRepo @Inject constructor(private val moviesApiService: MoviesApiService) {

//    suspend fun getAllMoviesDetails(): MovieResponse {
//        return moviesApiService.getAllMovies()
//    }

    fun getMoviesList() = Pager(
        config = PagingConfig(pageSize = 10, maxSize = 60),
        pagingSourceFactory = { MoviesPagingSource(moviesApiService) }).liveData

}