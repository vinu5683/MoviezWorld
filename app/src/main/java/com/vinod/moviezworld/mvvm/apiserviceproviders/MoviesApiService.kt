package com.vinod.moviezworld.mvvm.apiserviceproviders

import com.vinod.moviezworld.mvvm.models.MovieResponse
import com.vinod.moviezworld.mvvm.models.MoviesResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApiService {

    @GET("/")
    suspend fun getAllMovies(
//        @Query("type") type: String,
        @Query("s") searchWord: String,
        @Query("y") year: String,
        @Query("apikey") apiKey: String,
        @Query("page") page: String,
    ): MovieResponse

    @GET("/")
    suspend fun getAllMovies(
        @Query("s") searchWord: String,
        @Query("type") type: String,
        @Query("y") year: String,
        @Query("apikey") apiKey: String,
        @Query("page") page: String,
    ): MovieResponse
}