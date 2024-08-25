package com.fd.movie.data.mapper

import com.fd.movie.data.remote.responses.GenreResponse
import com.fd.movie.data.remote.responses.MovieResponse
import com.fd.movie.data.remote.responses.MoviesResponse
import com.fd.movie.ui.movie.model.Movie
import com.fd.movie.utils.Constants
import java.util.Locale

object MovieMapper {

    private var genres: List<GenreResponse>? = null
    suspend fun mapToUiModel(response: MoviesResponse?, genres: List<GenreResponse>): List<Movie> {
        this.genres = genres
        val list = mutableListOf<Movie>()
        response?.results?.forEach { list.add(mapToUiModel(it)) }
        return list
    }

    private suspend fun mapToUiModel(response: MovieResponse?): Movie {
        return Movie(
            id = MapHelper.get(response?.id),
            title = MapHelper.get(response?.title),
            backdropPath = MapHelper.get(response?.backdropPath),
            backdropUrl = "${Constants.IMAGE_URL}${response?.backdropPath}",
            genres = getGenreText(response?.genreIds),
            overview = MapHelper.get(response?.overview),
            posterPath = MapHelper.get(response?.posterPath),
            posterUrl = "${Constants.IMAGE_URL}${response?.posterPath}",
            releaseDate = MapHelper.get(response?.releaseDate),
            voteAverage = MapHelper.get(response?.voteAverage),
            formattedVoteAverage = getFormattedVoteAverage(response?.voteAverage)
        )
    }

    private fun getGenreText(genreIds: List<Int?>?): String {
        if (genreIds != null) {
            return try {
                genreIds.joinToString(", ") { genreId ->
                    val genre = genres?.find { it.id == genreId }
                    genre?.name ?: ""
                }
            } catch (e: NullPointerException) {
                e.printStackTrace()
                "-"
            }
        }
        return "-"
    }

    private fun getFormattedVoteAverage(average : Double?) : String {
        val avr = String.format("%.2f", average).toDouble()
        return "$avr / 10"
    }
}