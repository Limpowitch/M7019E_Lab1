package com.example.m7019e_lab1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m7019e_lab1.data.local.MovieDatabase
import com.example.m7019e_lab1.data.local.OfflineMovieRepository
import com.example.m7019e_lab1.data.remote.client.RetrofitClient
import com.example.m7019e_lab1.data.repository.CachedMoviesRepository
import com.example.m7019e_lab1.data.repository.MoviesRepository
import com.example.m7019e_lab1.data.repository.MoviesRepositoryImpl
import com.example.m7019e_lab1.models.Movie
import com.example.m7019e_lab1.models.Review
import com.example.m7019e_lab1.models.Video
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch



class MoviesViewModel(
    application: Application
) : AndroidViewModel(application) {

    // build the two backing repos
    private val localRepo  = OfflineMovieRepository(
        MovieDatabase.getDatabase(application).movieDao()
    )
    private val remoteRepo = MoviesRepositoryImpl(RetrofitClient.apiService)

    // wrap them in our cache‐aware repo
    private val repo = CachedMoviesRepository(remoteRepo, localRepo)

    // your existing state‐flows
    private val _gridItems      = MutableStateFlow<List<Movie?>>(emptyList())
    val    gridItems: StateFlow<List<Movie?>> = _gridItems

    private val _selectedMovie = MutableStateFlow<Movie?>(null)
    val    selectedMovie: StateFlow<Movie?> = _selectedMovie

    private val _reviews        = MutableStateFlow<List<Review>>(emptyList())
    val    reviews: StateFlow<List<Review>> = _reviews

    private val _videos         = MutableStateFlow<List<Video>>(emptyList())
    val    videos: StateFlow<List<Video>> = _videos

    init {
        viewModelScope.launch {
            // this will first read Room; if empty, it will call TMDB and populate Room
            _gridItems.value = repo.fetchTopRatedMovies()
        }
    }

    fun loadMovieDetails(id: Long) {
        viewModelScope.launch {
            _selectedMovie.value = repo.fetchMovieDetails(id)
            _reviews.value       = repo.fetchMovieReviews(id)
            _videos.value        = repo.fetchMovieVideos(id)
        }
    }
}