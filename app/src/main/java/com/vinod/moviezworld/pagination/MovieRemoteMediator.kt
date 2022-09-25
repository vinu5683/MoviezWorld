package com.vinod.moviezworld.pagination

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.vinod.moviezworld.BuildConfig
import com.vinod.moviezworld.db.MoviesDatabase
import com.vinod.moviezworld.mvvm.apiserviceproviders.MoviesApiService
import com.vinod.moviezworld.mvvm.models.RemoteMovieKeyModel
import com.vinod.moviezworld.mvvm.models.SearchItem

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val movieApi: MoviesApiService,
    private val moviesDB: MoviesDatabase,
    private val searchWord: String,
    private val type: String,
    private val year: String
) :
    RemoteMediator<Int, SearchItem>() {


    private val moviesDao = moviesDB.moviesDao()
    private val remoteKeysDao = moviesDB.remoteKeysDao()


    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, SearchItem>
    ): MediatorResult {

        return try {
            val currentPageNo: Int = when (loadType) {
                LoadType.REFRESH -> {
                    val remotekeys = getRemoteKeyClosestToCurrentPosition(state)
                    remotekeys?.next?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remotekeys = getRemoteKeyForFirstItem(state)
                    val prevPage =
                        remotekeys?.prev ?: return MediatorResult.Success(remotekeys != null)
                    prevPage
                }
                LoadType.APPEND -> {
                    val remotekeys = getRemoteKeyForLastItem(state)
                    val nextPage =
                        remotekeys?.next ?: return MediatorResult.Success(remotekeys != null)
                    nextPage
                }
            }
            val response =
                if (type.isNotEmpty()) movieApi.getAllMovies(
                    searchWord,
                    type,
                    year,
                    BuildConfig.API_KEY,
                    currentPageNo.toString()
                )
                else movieApi.getAllMovies(
                    searchWord,
                    year,
                    BuildConfig.API_KEY,
                    currentPageNo.toString()
                )
            val isLastPage = response.totalResults?.toInt()?.div(10) == currentPageNo

            val prevPage = if (currentPageNo == 1) null else currentPageNo - 1
            val nextPage = if (isLastPage) null else currentPageNo + 1

            moviesDB.withTransaction {

                if (loadType == LoadType.REFRESH) {
                    moviesDao.deleteMovies()
                    remoteKeysDao.deleteAllRemoteKeys()
                }

                moviesDao.addMovie(response.search)
                val keys = response.search.map { movie ->

                    RemoteMovieKeyModel(movie.imdbID, prevPage, nextPage)
                }
                remoteKeysDao.addAllRemoteKeys(keys)
            }
            return MediatorResult.Success(isLastPage)
        } catch (e: Exception) {
            e.printStackTrace()
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, SearchItem>): RemoteMovieKeyModel? {
        return state.anchorPosition?.let { pos ->
            state.closestItemToPosition(pos)?.imdbID.let { id ->
                remoteKeysDao.getRemoteKeys(id = id.toString())
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, SearchItem>): RemoteMovieKeyModel? {
        return state.pages.firstOrNull() { it.data.isNotEmpty() }?.data?.firstOrNull()?.let {
            remoteKeysDao.getRemoteKeys(it.imdbID)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, SearchItem>): RemoteMovieKeyModel? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let {
            remoteKeysDao.getRemoteKeys(it.imdbID)
        }
    }
}