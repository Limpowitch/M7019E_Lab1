//Simple interface for fetching top rated movies from TMDB api and mapping against domain models

package com.example.m7019e_lab1.data.repository

import com.example.m7019e_lab1.models.Movie
import com.example.m7019e_lab1.models.Review
import com.example.m7019e_lab1.models.Video

interface MoviesRepository {
    suspend fun fetchTopRatedMovies(): List<Movie>

    suspend fun fetchPopularMovies(): List<Movie>

    suspend fun fetchMovieDetails(id: Long): Movie

    suspend fun fetchMovieReviews(id: Long): List<Review>

    suspend fun fetchMovieVideos(id: Long): List<Video>
}