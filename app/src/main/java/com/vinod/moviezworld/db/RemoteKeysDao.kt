package com.vinod.moviezworld.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vinod.moviezworld.mvvm.models.RemoteMovieKeyModel
import org.jetbrains.annotations.NotNull


@Dao
interface RemoteKeysDao {

    @Query("SELECT * FROM remotekeys WHERE id =:id")
    suspend fun getRemoteKeys(id: String): RemoteMovieKeyModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<RemoteMovieKeyModel>)

    @Query("DELETE FROM remotekeys")
    suspend fun deleteAllRemoteKeys()

}