package com.example.m7019e_lab1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.m7019e_lab1.data.local.MovieDatabase
import com.example.m7019e_lab1.data.local.toDomain
import com.example.m7019e_lab1.data.workers.enqueuers.enqueueFetchMoviesWork
import com.example.m7019e_lab1.models.Movie
import com.example.m7019e_lab1.models.Review
import com.example.m7019e_lab1.models.Video
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.onEach

class MoviesViewModel(
    application: Application
) : AndroidViewModel(application) {

    // 1) Grab the DAO
    private val movieDao = MovieDatabase
        .getDatabase(application)
        .movieDao()

    companion object { private const val TAG = "FetchMoviesWorker" }

    fun moviesByCategoryOrFetch(category: String): StateFlow<List<Movie>> =
        movieDao
            .getMoviesForCategory(category)
            .onEach { cached ->
                if (cached.isEmpty()) {
                    enqueueFetchMoviesWork(
                        getApplication<Application>().applicationContext,
                        category
                    )
                }
            }
            .map { list -> list.map { it.toDomain() } }
            .stateIn(
                scope   = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 0L),
                initialValue = emptyList()
            )

    private val _selectedMovie = MutableStateFlow<Movie?>(null)
    val    selectedMovie: StateFlow<Movie?> = _selectedMovie

    private val _reviews = MutableStateFlow<List<Review>>(emptyList())
    val    reviews: StateFlow<List<Review>>      = _reviews

    private val _videos  = MutableStateFlow<List<Video>>(emptyList())
    val    videos: StateFlow<List<Video>>        = _videos

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage as StateFlow<String?>

    private val detailsCoordinator = MovieDetailsCoordinator(
        context       = getApplication(),
        scope         = viewModelScope,
        selectedMovie = _selectedMovie,
        reviews       = _reviews,
        videos        = _videos,
        errorMessage  = _errorMessage
    )

    fun loadMovieDetails(id: Long) {        // Handles all the logic for retrieving movie details
        detailsCoordinator.loadDetails(id)
    }
}
