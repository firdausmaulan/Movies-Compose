package com.fd.movie.ui.movie.list

import com.fd.movie.ui.movie.model.Movie

interface MoviesItemListener {
    fun onClick(movie: Movie)
}