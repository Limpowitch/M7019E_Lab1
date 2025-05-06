package com.example.m7019e_lab1.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.m7019e_lab1.viewmodel.MoviesViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.m7019e_lab1.MovieAppScreen
import com.example.m7019e_lab1.models.Movie
import com.example.m7019e_lab1.utils.Constants
import androidx.compose.foundation.lazy.grid.items
import androidx.work.WorkManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow

@Composable
fun MovieGrid(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    moviesFlow: StateFlow<List<Movie>>,
    category: String
) {
    Log.d("MovieGrid", "Current category = $category")

    val movies by moviesFlow.collectAsState()

    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(category) {
        isLoading = true
        delay(1_500)
        isLoading = false
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.Center)
            )
            return@Box
        }

    if (movies.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No internet connection")
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            items(movies) { movie ->
                MovieGridItemCard(
                    navController,
                    movie = movie,
                    modifier = Modifier
                        .height(220.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
    }
}

@Composable
fun MovieGridItemCard(
    navController: NavHostController,
    movie: Movie?,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier
            .height(220.dp)
            .fillMaxWidth()
            .clickable {
                navController.navigate("${MovieAppScreen.MovieInformation.name}/${movie?.id}")
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val rawPath    = movie?.posterPath.orEmpty().trim()
            val safePath   = rawPath.takeIf { it.startsWith("/") } ?: "/$rawPath"
            val posterUrl  = "${Constants.POSTER_IMAGE_BASE_URL}${Constants.POSTER_IMAGE_BASE_WIDTH}$safePath"

            Log.d("MovieGrid", "posterPath = ${movie?.posterPath}")
            Log.d("MovieGrid", "→ posterUrl = $posterUrl")

            AsyncImage(
                model = posterUrl,
                contentDescription = movie?.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = movie?.title ?: "",
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


