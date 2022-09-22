package com.vinod.moviezworld.mvvm.apiserviceproviders

import com.vinod.moviezworld.mvvm.models.MoviesResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApiService {

    @GET("api/movies")
    suspend fun getAllMovies(@Query("page") page: Int = 0): List<MoviesResponseModel>
}