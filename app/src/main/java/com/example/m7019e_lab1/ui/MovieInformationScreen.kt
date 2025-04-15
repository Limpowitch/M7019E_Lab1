package com.example.m7019e_lab1.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.m7019e_lab1.viewmodel.MoviesViewModel

@Composable
fun MovieInformation(navController: NavHostController, movieId: String?) {
    val id = movieId?.toLongOrNull()
    val moviesViewModel: MoviesViewModel = viewModel()
    val movie = remember(id) {id?.let {moviesViewModel.getMovie(it)}}

    Column (
        modifier = Modifier.padding(8.dp)
    ) {
        // Text(text = movie?.title ?: "Unknown Title")
        Spacer(modifier = Modifier.size(8.dp))
        Text(text = movie?.overview ?: "No description available")
    }

}

