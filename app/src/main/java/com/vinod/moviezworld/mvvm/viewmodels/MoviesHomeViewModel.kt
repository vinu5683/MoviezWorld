package com.vinod.moviezworld.mvvm.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vinod.moviezworld.mvvm.models.MoviesResponseModel
import com.vinod.moviezworld.mvvm.repository.MoviesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MoviesHomeViewModel @Inject constructor(private val moviesRepo: MoviesRepo) {

    private val _allMoviesList = MutableLiveData<List<MoviesResponseModel>>()
    val allMoviesList = _allMoviesList

    fun requestAllMoviesList() {
        CoroutineScope(Dispatchers.IO).launch {
            _allMoviesList.postValue(moviesRepo.getAllMoviesDetails())
        }
    }

}