package com.fd.movie.data.repositories

import com.fd.movie.data.remote.responses.GenreResponse
import com.fd.movie.data.remote.responses.MoviesResponse

interface MovieRepository {
    suspend fun searchMovies(query: String, page: Int): MoviesResponse
    suspend fun getGenres(): List<GenreResponse>?
}