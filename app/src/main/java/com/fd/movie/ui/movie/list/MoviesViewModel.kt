package com.fd.movie.ui.movie.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.fd.movie.data.remote.responses.GenreResponse
import com.fd.movie.data.repositories.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val _repository: MovieRepository) : ViewModel() {

    val repository get() = _repository
    private val _genres = mutableListOf<GenreResponse>()
    val genres get() = _genres

    init {
        viewModelScope.launch {
            _repository.getGenres()?.let { _genres.addAll(it) }
        }
    }

    fun searchMovies(query: String) =
        Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { MoviesPagingSource(this, query) }
        ).flow
}