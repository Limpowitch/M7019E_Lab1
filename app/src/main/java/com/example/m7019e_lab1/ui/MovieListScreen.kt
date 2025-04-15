package com.example.m7019e_lab1.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.m7019e_lab1.database.Movies
import com.example.m7019e_lab1.models.Movie
import androidx.compose.foundation.lazy.items
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

@Composable
fun MovieList(navController: NavHostController, modifier: Modifier = Modifier) {
    val viewModel: MoviesViewModel = viewModel()
    val moviesState by viewModel.movies.collectAsState()

    LazyColumn(modifier = modifier) {
        items(moviesState) { movie ->
            MovieItemCard(navController, movie = movie, modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun MovieItemCard(navController: NavHostController, movie: Movie, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Row(
            modifier = Modifier.padding(8.dp).clickable {
                navController.navigate("${MovieAppScreen.MovieInformation.name}/${movie.id}")
            }
        ) {
            Box {
                AsyncImage(
                    model = Constants.POSTER_IMAGE_BASE_URL+Constants.POSTER_IMAGE_BASE_WIDTH
                            +movie.posterPath,
                    contentDescription = movie.title,
                    modifier = modifier.width(92.dp).height(138.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Column {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = movie.genre,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = movie.releaseDate,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = movie.overview,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.size(8.dp))
            }
        }
    }
}

