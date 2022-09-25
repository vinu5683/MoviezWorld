package com.vinod.moviezworld.pagination


import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vinod.moviezworld.BuildConfig
import com.vinod.moviezworld.mvvm.apiserviceproviders.MoviesApiService
import com.vinod.moviezworld.mvvm.models.SearchItem

class MoviesPagingSource(
    private val moviesApiService: MoviesApiService,
    private val searchWord: String,
    val type: String = "",
    private val year: String
) : PagingSource<Int, SearchItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchItem> {

        return try {
            val position = params.key ?: 1
            val response = moviesApiService.getAllMovies(
                searchWord,
                type,
                year,
                BuildConfig.API_KEY,
                position.toString()
            )
            Log.d("Taggg", response.toString())
            LoadResult.Page(
                data = response.search,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position >= (response.totalResults?.toInt()
                        ?.div(10)!!)
                ) null else position + 1
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, SearchItem>): Int? {

        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1);
        }

    }
}