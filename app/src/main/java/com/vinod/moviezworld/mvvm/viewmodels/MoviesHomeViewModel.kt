package com.vinod.moviezworld.mvvm.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vinod.moviezworld.mvvm.models.SearchItem
import com.vinod.moviezworld.mvvm.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MoviesHomeViewModel @Inject constructor(private val moviesRepository: MoviesRepository) : ViewModel() {

    var allMoviesList: LiveData<PagingData<SearchItem>>? = null


    fun getCategoryData(searchWord: String, type: String = "", year: String) {
        viewModelScope.launch {
            moviesRepository.getMoviesList(searchWord, type, year)
                .cachedIn(viewModelScope).let {
                    allMoviesList = it as MutableLiveData<PagingData<SearchItem>>
                }
        }
    }

}