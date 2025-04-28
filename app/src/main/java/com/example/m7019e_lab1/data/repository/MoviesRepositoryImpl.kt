//Domain level implementation of function to retrieve data from TMDB api
//Likewise makes sure to map the raw DTOs against our domain models

package com.example.m7019e_lab1.data.repository

import com.example.m7019e_lab1.data.remote.api.TmdbApiService
import com.example.m7019e_lab1.data.remote.dto.MovieGridItemDto
import com.example.m7019e_lab1.data.remote.dto.ReviewDto
import com.example.m7019e_lab1.data.remote.mapper.toDomain
import com.example.m7019e_lab1.models.MovieGridItem
import com.example.m7019e_lab1.models.Movie
import com.example.m7019e_lab1.models.Review

class MoviesRepositoryImpl(
    private val api: TmdbApiService
) : MoviesRepository {

    override suspend fun fetchTopRatedMovies(): List<MovieGridItem> =
        api.getTopRatedMovies()
            .results
            .map(MovieGridItemDto::toDomain)

    override suspend fun fetchMovieDetails(id: Long): Movie =
        api.getMovieDetails(id)
            .toDomain()

    override suspend fun fetchMovieReviews(id: Long): List<Review> =
        api.getMovieReviews(id)
            .results
            .map(ReviewDto::toDomain)

}