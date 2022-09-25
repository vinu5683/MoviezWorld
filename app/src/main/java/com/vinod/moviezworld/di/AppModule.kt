package com.vinod.moviezworld.di

import android.content.Context
import androidx.room.Room
import com.vinod.moviezworld.db.MoviesDatabase
import com.vinod.moviezworld.mvvm.apiserviceproviders.MoviesApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    var interceptor = HttpLoggingInterceptor()

    @Provides
    @Singleton
    fun getRetrofit(): Retrofit {
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder().baseUrl("https://www.omdbapi.com/")
            .addConverterFactory(GsonConverterFactory.create()).client(client).build();
    }

    @Provides
    @Singleton
    fun provideMoviesApiService(): MoviesApiService {
        return getRetrofit().create(MoviesApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MoviesDatabase {
        return Room.databaseBuilder(context, MoviesDatabase::class.java, "movie").build()
    }
}