package com.vinod.moviezworld.mvvm.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @field:SerializedName("Response")
    val response: String? = null,

    @field:SerializedName("totalResults")
    val totalResults: String? = null,

    @field:SerializedName("Search")
    val search: List<SearchItem>
)

@Entity(tableName = "movie")
data class SearchItem(

    @field:SerializedName("Type")
    val type: String? = null,

    @field:SerializedName("Year")
    val year: String? = null,

    @PrimaryKey(autoGenerate = false)
    @field:SerializedName("imdbID")
    val imdbID: String = "1111",

    @field:SerializedName("Poster")
    val poster: String? = null,

    @field:SerializedName("Title")
    val title: String? = null
)
