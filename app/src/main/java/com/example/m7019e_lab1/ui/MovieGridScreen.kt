package com.example.m7019e_lab1.ui

import android.util.Log
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.m7019e_lab1.models.Movie
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.m7019e_lab1.MovieAppScreen
import com.example.m7019e_lab1.utils.Constants
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.m7019e_lab1.viewmodel.MoviesViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import com.example.m7019e_lab1.models.MovieGridItem

@Composable
fun MovieGrid(
    navController: NavHostController,
    moviesViewModel: MoviesViewModel,
    modifier: Modifier = Modifier
) {
    val items by moviesViewModel.gridItems.collectAsState(initial = emptyList())

    LazyVerticalGrid (
        columns = GridCells.Adaptive(minSize = 128.dp),
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(items) { gridItem ->
            MovieGridItemCard(
                navController,
                movie = gridItem,
                modifier = Modifier.height(220.dp).fillMaxWidth()
            )
        }
    }
}

@Composable
fun MovieGridItemCard(
    navController: NavHostController,
    movie: MovieGridItem,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier
            .height(220.dp)
            .fillMaxWidth()
            .clickable {
                navController.navigate("${MovieAppScreen.MovieInformation.name}/${movie.id}")
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val rawPath    = movie.posterPath.orEmpty().trim()
            val safePath   = rawPath.takeIf { it.startsWith("/") } ?: "/$rawPath"
            val posterUrl  = "${Constants.POSTER_IMAGE_BASE_URL}${Constants.POSTER_IMAGE_BASE_WIDTH}$safePath"

            Log.d("MovieGrid", "posterPath = ${movie.posterPath}")
            Log.d("MovieGrid", "→ posterUrl = $posterUrl")

            AsyncImage(
                model = posterUrl,
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = movie.title,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

