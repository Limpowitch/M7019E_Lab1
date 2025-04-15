package com.example.m7019e_lab1.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
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
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = movie?.overview ?: "No description available")

            Spacer(modifier = Modifier.size(8.dp))

            Row( modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Button(
                    onClick = {
                        val url = movie?.movie_homepage_url
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                    },
                    modifier = Modifier.size(width = 150.dp, height = 60.dp).padding(8.dp)
                ) {
                    Text(text = "Homepage")
                }
                Button(
                    onClick = {
                        val imdbPackageName = "com.imdb.mobile" // Update this with the correct package name if needed
                        val movieUrl = "https://www.imdb.com/title/${movie?.imdb_id}"

                        val launchIntent = context.packageManager.getLaunchIntentForPackage(imdbPackageName)?.apply {
                            // Set the data to the movie URL.
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
                    modifier = Modifier.size(width = 150.dp, height = 60.dp).padding(8.dp)
                ) {
                    Text(text = "Open IMDb")
                }
            }
        }
    }
}

