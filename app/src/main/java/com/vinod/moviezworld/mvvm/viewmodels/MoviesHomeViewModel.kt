package com.vinod.moviezworld.mvvm.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.vinod.moviezworld.mvvm.models.MovieResponse
import com.vinod.moviezworld.mvvm.models.MoviesResponseModel
import com.vinod.moviezworld.mvvm.repository.MoviesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MoviesHomeViewModel @Inject constructor(moviesRepo: MoviesRepo) : ViewModel() {

    private val _allMoviesList = MutableLiveData<MovieResponse>()
    val allMoviesList = moviesRepo.getMoviesList().cachedIn(viewModelScope)

//    fun requestAllMoviesList() {
//        CoroutineScope(Dispatchers.IO).launch {
//            _allMoviesList.postValue()
//        }
//    }

}