package com.example.m7019e_lab1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m7019e_lab1.data.remote.client.RetrofitClient
import com.example.m7019e_lab1.data.repository.MoviesRepository
import com.example.m7019e_lab1.data.repository.MoviesRepositoryImpl
import com.example.m7019e_lab1.database.Movies
import com.example.m7019e_lab1.models.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class MoviesViewModel : ViewModel() {
    // build your repo here
    private val repository = MoviesRepositoryImpl(RetrofitClient.apiService)

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies

    init {
        viewModelScope.launch {
            _movies.value = repository.fetchTopRatedMovies()
        }
    }

    fun getMovie(id: Long): Movie? =
        _movies.value.find { it.id == id }
}