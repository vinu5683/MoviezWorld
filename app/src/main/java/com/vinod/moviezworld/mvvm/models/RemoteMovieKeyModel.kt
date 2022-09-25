package com.vinod.moviezworld.mvvm.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remotekeys")
data class RemoteMovieKeyModel(
    @PrimaryKey(autoGenerate = false) val id: String,
    val prev: Int?,
    val next: Int?
) {
}