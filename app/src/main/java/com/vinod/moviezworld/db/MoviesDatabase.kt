package com.vinod.moviezworld.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vinod.moviezworld.mvvm.models.RemoteMovieKeyModel
import com.vinod.moviezworld.mvvm.models.SearchItem


@Database(entities = [SearchItem::class, RemoteMovieKeyModel::class], version = 1, exportSchema = false)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun moviesDao(): MovieDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}