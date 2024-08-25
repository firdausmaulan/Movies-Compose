package com.fd.movie.ui.movie.list

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fd.movie.R
import com.fd.movie.ui.common.DebounceTextField
import com.fd.movie.ui.movie.detail.MovieDetailActivity
import com.fd.movie.ui.movie.model.Movie
import com.fd.movie.utils.Constants
import com.fd.movie.utils.PermissionUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesActivity : ComponentActivity() {

    private val viewModel: MoviesViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PermissionUtil.requestNotificationPermission(this)
        setContent {
            var searchQuery by remember { mutableStateOf(Constants.DEFAULT_QUERY) }
            Box(Modifier.safeDrawingPadding()) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color.White,
                                titleContentColor = Color.Black,
                            ),
                            title = {
                                Text(
                                    getString(R.string.app_name),
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        )
                    },
                ) { innerPadding ->
                    Surface(modifier = Modifier.padding(top = innerPadding.calculateTopPadding())) {
                        Column(Modifier.background(color = Color.LightGray)) {
                            // SearchBar
                            Box(Modifier.padding(8.dp)) {
                                DebounceTextField(placeholderText = getString(R.string.search_hint)) {
                                    println("search query : $it")
                                    searchQuery = it.ifEmpty { Constants.DEFAULT_QUERY }
                                }
                            }
                            // List Movie
                            MoviesListView(
                                movies = viewModel.searchMovies(searchQuery),
                                listener = object : MoviesItemListener {
                                    override fun onClick(movie: Movie) {
                                        val intent = Intent(
                                            this@MoviesActivity,
                                            MovieDetailActivity::class.java
                                        )
                                        intent.putExtra(Constants.KEY_MOVIE, movie)
                                        startActivity(intent)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMoviesActivity() {
    MoviesActivity()
}