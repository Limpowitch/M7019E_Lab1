package com.example.m7019e_lab1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m7019e_lab1.database.Movies
import com.example.m7019e_lab1.models.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MoviesViewModel : ViewModel() {
    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies

    init {
        viewModelScope.launch {
            val loadedMovies = Movies().getMovies()
            _movies.value = loadedMovies
        }
    }

    fun getMovie(id: Long): Movie? {
        return _movies.value.find { it.id == id }
    }
}