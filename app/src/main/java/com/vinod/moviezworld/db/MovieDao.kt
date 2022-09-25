package com.vinod.moviezworld.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vinod.moviezworld.mvvm.models.SearchItem


@Dao
interface MovieDao {

    @Query("SELECT * from movie")
    fun getMovies():PagingSource<Int, SearchItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovie(searchItem: List<SearchItem>)

    @Query("DELETE From movie")
    suspend fun deleteMovies()
}