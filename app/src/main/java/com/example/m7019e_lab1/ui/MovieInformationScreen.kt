package com.example.m7019e_lab1.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun MovieInformation(navController: NavHostController, movieId: String?, modifier: Modifier = Modifier) {
    val id = movieId?.toLongOrNull()
    val moviesViewModel: MoviesViewModel = viewModel()
    val movie = remember(id) {id?.let {moviesViewModel.getMovie(it)}}
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column (
            modifier = Modifier.padding(8.dp)
        ) {
            Spacer(modifier = Modifier.size(16.dp))

            Text(
                text = movie?.title ?: "No movie title available",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.size(8.dp))

            Row {
                Text(text = movie?.releaseDate ?: "No release date available",
                    style = MaterialTheme.typography.bodySmall,
                )
            }

            Spacer(modifier = Modifier.size(8.dp))

            Divider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outline
            )

            Spacer(modifier = Modifier.size(8.dp))

            Row {
                Box {
                    AsyncImage(
                        model = Constants.POSTER_IMAGE_BASE_URL+Constants.POSTER_IMAGE_BASE_WIDTH
                                +movie?.posterPath,
                        contentDescription = movie?.title,
                        modifier = modifier.width(144.dp).height(215.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.size(8.dp))

                Text(
                    text = movie?.overview ?: "No description available",
                    maxLines = 4,
                    style = MaterialTheme.typography.bodySmall,
                )
            }

            Spacer(modifier = Modifier.size(8.dp))

            movie?.genre?.let { genres ->
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(genres) { genre ->
                        Text(
                            text = genre,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .border(
                                    width = (1.5).dp,
                                    color = Color.Gray,
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.size(24.dp))

            Column( modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        val url = movie?.movie_homepage_url
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                    },
                    modifier = Modifier.size(width = 200.dp, height = 60.dp).padding(8.dp)
                ) {
                    Text(text = "Homepage")
                }
                Button(
                    onClick = {
                        val imdbPackageName = "com.imdb.mobile"
                        val movieUrl = "https://www.imdb.com/title/${movie?.imdb_id}"

                        val launchIntent = context.packageManager.getLaunchIntentForPackage(imdbPackageName)?.apply {
                            data = Uri.parse(movieUrl)
                        }
                        try {
                            if (launchIntent != null) {
                                context.startActivity(launchIntent)
                            } else {
                                throw ActivityNotFoundException("IMDb app not found")
                            }
                        } catch (ex: ActivityNotFoundException) {
                            Log.e("OpenImdbMovieButton", "Error launching IMDb app: ${ex.message}")
                            // Fallback: open the URL in a browser
                            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(movieUrl)))
                        }
                    },
                    modifier = Modifier.size(width = 200.dp, height = 60.dp).padding(8.dp)
                ) {
                    Text(text = "Open IMDb")
                }
            }
        }
    }
}

