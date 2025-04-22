//Simple interface for fetching top rated movies from TMDB api and mapping against domain models

package com.example.m7019e_lab1.data.repository

import com.example.m7019e_lab1.models.Movie

interface MoviesRepository {

    suspend fun fetchTopRatedMovies(): List<Movie>
}