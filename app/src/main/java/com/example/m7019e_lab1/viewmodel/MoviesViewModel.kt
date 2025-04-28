package com.example.m7019e_lab1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m7019e_lab1.data.remote.client.RetrofitClient
import com.example.m7019e_lab1.data.repository.MoviesRepository
import com.example.m7019e_lab1.data.repository.MoviesRepositoryImpl
import com.example.m7019e_lab1.models.Movie
import com.example.m7019e_lab1.models.MovieGridItem
import com.example.m7019e_lab1.models.Review
import com.example.m7019e_lab1.models.Video
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class MoviesViewModel(
    private val repo: MoviesRepository = MoviesRepositoryImpl(RetrofitClient.apiService)
) : ViewModel() {
    private val _gridItems = MutableStateFlow<List<MovieGridItem>>(emptyList())
    val gridItems: StateFlow<List<MovieGridItem>> = _gridItems

    private val _selectedMovie = MutableStateFlow<Movie?>(null)
    val selectedMovie: StateFlow<Movie?> = _selectedMovie

    private val _reviews = MutableStateFlow<List<Review>>(emptyList())
    val reviews: StateFlow<List<Review>> = _reviews

    private val _videos = MutableStateFlow<List<Video>>(emptyList())
    val videos: StateFlow<List<Video>> = _videos

    init {
        viewModelScope.launch {
            _gridItems.value = repo.fetchTopRatedMovies()
        }
    }

    // UNCOMMENT & IMPLEMENT:
    fun loadMovieDetails(id: Long) {
        viewModelScope.launch {
            _selectedMovie.value = repo.fetchMovieDetails(id)
            _reviews.value       = repo.fetchMovieReviews(id)
            _videos.value        = repo.fetchMovieVideos(id)
        }
    }
}