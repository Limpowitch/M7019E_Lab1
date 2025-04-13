package com.example.m7019e_lab1.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun MovieList(modifier: Modifier = Modifier) {
    val movieList = Movies().getMovies()

    LazyColumn(modifier = modifier) {
        items(movieList) { movie ->
            MovieItemCard(movie, modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun MovieItemCard(movie: Movie, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Row {
            Box {

            }
            Column {
                Text (text = movie.title,
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.size(8.dp))

                Text(text = movie.releaseDate,
                    style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.size(8.dp))

                Text(text = movie.overview,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis)
                Spacer(modifier = Modifier.size(8.dp))
            }
        }
    }
}

