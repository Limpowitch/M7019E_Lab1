package com.example.m7019e_lab1.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.m7019e_lab1.utils.Constants
import com.example.m7019e_lab1.viewmodel.MoviesViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.style.TextOverflow


@Composable
fun MovieInformation(
    navController: NavHostController,
    movieId: String?,
    moviesViewModel: MoviesViewModel,
    modifier: Modifier = Modifier
) {
    val id = movieId?.toLongOrNull() ?: return

    LaunchedEffect(id) {
        moviesViewModel.loadMovieDetails(id)
    }

    val movie by moviesViewModel
        .selectedMovie
        .collectAsState(initial = null)

    val reviews by moviesViewModel.reviews.collectAsState(initial = emptyList())

    val videos by moviesViewModel.videos.collectAsState(initial = emptyList())

    val context = LocalContext.current

    movie?.let { m ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            item {
                Text(
                    text = m.title,
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            if (videos.isNotEmpty()) {
                item {
                    Text("Videos", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) {
                        items(
                            items = videos,
                            key    = { it.id }
                        ) { video ->
                            Card(modifier = Modifier.width(300.dp)) {
                                YouTubePlayerView(
                                    videoId  = video.key,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                )
                            }
                        }
                    }
                }
            }

            item {
                Text(
                    text = m.releaseDate,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(Modifier.height(8.dp))
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outline
                )
            }

            item {
                Row {
                    AsyncImage(
                        model = Constants.POSTER_IMAGE_BASE_URL +
                                Constants.POSTER_IMAGE_BASE_WIDTH +
                                m.posterPath,
                        contentDescription = m.title,
                        modifier = Modifier
                            .width(144.dp)
                            .height(215.dp),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = m.overview,
                        maxLines = 6,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(m.genres) { genre ->
                        Text(
                            text = genre,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .border(
                                    width = 1.5.dp,
                                    color = Color.Gray,
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            Intent(Intent.ACTION_VIEW, Uri.parse(m.homepageUrl))
                                .also(context::startActivity)
                        },
                        modifier = Modifier
                            .size(width = 200.dp, height = 60.dp)
                            .padding(8.dp)
                    ) {
                        Text("Homepage")
                    }
                    Button(
                        onClick = {
                            val imdbUrl = "https://www.imdb.com/title/${m.imdbId}"
                            try {
                                val intent = context.packageManager
                                    .getLaunchIntentForPackage("com.imdb.mobile")
                                    ?.apply { data = Uri.parse(imdbUrl) }
                                    ?: throw ActivityNotFoundException()
                                context.startActivity(intent)
                            } catch (_: ActivityNotFoundException) {
                                Intent(Intent.ACTION_VIEW, Uri.parse(imdbUrl))
                                    .also(context::startActivity)
                            }
                        },
                        modifier = Modifier
                            .size(width = 200.dp, height = 60.dp)
                            .padding(8.dp)
                    ) {
                        Text("Open IMDb")
                    }
                }
            }

            if (reviews.isNotEmpty()) {
                item {
                    Text("Reviews", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) {
                        items(reviews) { review ->
                            Card(
                                modifier = Modifier
                                    .width(280.dp)
                                    .wrapContentHeight()
                            ) {
                                Column(Modifier.padding(12.dp)) {
                                    Text(
                                        text = "“${review.content.take(200)}…”",
                                        style = MaterialTheme.typography.bodySmall,
                                        maxLines = 4,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Spacer(Modifier.height(6.dp))
                                    Text(
                                        text = "- ${review.author}",
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                    review.rating?.let { r ->
                                        Text("Rating: $r/10", style = MaterialTheme.typography.labelSmall)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

