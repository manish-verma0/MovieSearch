package com.manish.moviesearch.views.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.manish.moviesearch.core.utils.UIState
import com.manish.moviesearch.data.model.Movie
import com.manish.moviesearch.data.repo.MovieRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repoImpl: MovieRepoImpl):ViewModel() {

    private val _mainItem = MutableStateFlow<UIState<Movie>>(UIState.Loading)
    val mainItem: StateFlow<UIState<Movie>> = _mainItem

    fun fetchMovie(title: String) {
        viewModelScope.launch {
            _mainItem.emit(UIState.Loading)
            repoImpl.getMovieByTitle(title)
                .flowOn(Dispatchers.IO)
                .catch {
                    _mainItem.emit(UIState.Failure(it))
                }
                .collect {
                    _mainItem.emit(UIState.Success(it))
                }
        }
    }
}