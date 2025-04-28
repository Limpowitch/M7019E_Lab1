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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.m7019e_lab1.MovieAppScreen
import com.example.m7019e_lab1.utils.Constants
import com.example.m7019e_lab1.viewmodel.MoviesViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

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

    // collect the detail state
    val movie by moviesViewModel
        .selectedMovie
        .collectAsState(initial = null)

    val reviews by moviesViewModel.reviews.collectAsState(initial = emptyList())


    val context = LocalContext.current

    movie?.let { m ->
        Box(modifier = modifier.fillMaxSize()) {
            Column(modifier = Modifier.padding(8.dp)) {
                Spacer(Modifier.height(16.dp))

                Text(
                    text = m.title,
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(Modifier.height(8.dp))

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

                Spacer(Modifier.height(8.dp))

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

                Spacer(Modifier.height(16.dp))

                // genres chips
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

                Spacer(Modifier.height(24.dp))

                // buttons
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

                if (reviews.isNotEmpty()) {
                    Spacer(Modifier.height(24.dp))
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

