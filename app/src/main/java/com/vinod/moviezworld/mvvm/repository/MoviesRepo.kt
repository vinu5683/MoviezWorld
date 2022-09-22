package com.vinod.moviezworld.mvvm.repository

import com.vinod.moviezworld.mvvm.apiserviceproviders.MoviesApiService
import com.vinod.moviezworld.mvvm.models.MoviesResponseModel
import javax.inject.Inject

class MoviesRepo @Inject constructor(private val moviesApiService: MoviesApiService) {

    suspend fun getAllMoviesDetails(): List<MoviesResponseModel> {
        return moviesApiService.getAllMovies()
    }

}