package com.fd.movie.ui.movie.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.fd.movie.ui.movie.model.Movie
import kotlinx.coroutines.flow.Flow

@Composable
fun MoviesListView(movies: Flow<PagingData<Movie>>, listener: MoviesItemListener) {
    val movieItems: LazyPagingItems<Movie> = movies.collectAsLazyPagingItems()

    LazyColumn {
        items(movieItems.itemCount) { item ->
            Box(modifier = Modifier.padding(8.dp)) {
                MoviesItem(item = movieItems[item], listener)
            }
        }
        movieItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    // Show loading indicator during initial load
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                loadState.append is LoadState.Loading -> {
                    // Show loading indicator at the end of the list
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                loadState.refresh is LoadState.Error -> {
                    // Handle initial load error
                    val e = movieItems.loadState.refresh as LoadState.Error
                    item { Text("Error: ${e.error.localizedMessage}") }
                }

                loadState.append is LoadState.Error -> {
                    // Handle append error
                    val e = movieItems.loadState.append as LoadState.Error
                    item { Text("Error: ${e.error.localizedMessage}") }
                }
            }
        }
    }
}
