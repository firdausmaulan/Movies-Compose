package com.fd.movie.ui.movie.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.fd.movie.R
import com.fd.movie.ui.movie.model.Movie
import com.fd.movie.utils.Constants
import com.fd.movie.utils.parcelable

class MovieDetailActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val movie = intent.parcelable<Movie>(Constants.KEY_MOVIE)
        setContent {
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
                                    getString(R.string.movie_detail),
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.SemiBold
                                )
                            },
                            navigationIcon = {
                                IconButton(onClick = { finish() }) {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_back),
                                        contentDescription = ""
                                    )
                                }
                            }
                        )
                    },
                ) { innerPadding ->
                    Surface(modifier = Modifier.padding(top = innerPadding.calculateTopPadding())) {
                        Column(
                            Modifier.verticalScroll(state = rememberScrollState())
                        ) {
                            println(movie?.backdropUrl)
                            AsyncImage(
                                model = ImageRequest
                                    .Builder(baseContext)
                                    .data(movie?.backdropUrl)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentScale = ContentScale.FillBounds,
                                error = painterResource(id = R.drawable.placeholder),
                                placeholder = painterResource(id = R.drawable.placeholder)
                            )
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text(
                                    getString(R.string.title),
                                    modifier = Modifier.fillMaxWidth(),
                                    color = Color.Black,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp
                                )
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = movie?.title.toString(),
                                    modifier = Modifier.fillMaxWidth(),
                                    color = Color.Black,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 16.sp
                                )
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    getString(R.string.genre),
                                    modifier = Modifier.fillMaxWidth(),
                                    color = Color.Black,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp
                                )
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = movie?.genres.toString(),
                                    modifier = Modifier.fillMaxWidth(),
                                    color = Color.Black,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 16.sp
                                )
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    getString(R.string.rating),
                                    modifier = Modifier.fillMaxWidth(),
                                    color = Color.Black,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp
                                )
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = movie?.formattedVoteAverage.toString(),
                                    modifier = Modifier.fillMaxWidth(),
                                    color = Color.Black,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 16.sp
                                )
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    getString(R.string.release_date),
                                    modifier = Modifier.fillMaxWidth(),
                                    color = Color.Black,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp
                                )
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = movie?.releaseDate.toString(),
                                    modifier = Modifier.fillMaxWidth(),
                                    color = Color.Black,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 16.sp
                                )
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    getString(R.string.overview),
                                    modifier = Modifier.fillMaxWidth(),
                                    color = Color.Black,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp
                                )
                                Spacer(Modifier.height(4.dp))
                                movie?.overview?.let {
                                    Text(
                                        text = it,
                                        modifier = Modifier.fillMaxWidth(),
                                        color = Color.Black,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 16.sp
                                    )
                                }
                            }
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
    MovieDetailActivity()
}