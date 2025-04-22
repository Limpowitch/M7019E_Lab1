//Domain level implementation of function to retrieve data from TMDB api
//Likewise makes sure to map the raw DTOs against our domain models

package com.example.m7019e_lab1.data.repository

import com.example.m7019e_lab1.data.remote.api.TmdbApiService
import com.example.m7019e_lab1.data.remote.mapper.toDomain
import com.example.m7019e_lab1.models.Movie
import com.example.m7019e_lab1.data.remote.dto.MovieDto


class MoviesRepositoryImpl(
    private val api: TmdbApiService
) : MoviesRepository {
    override suspend fun fetchTopRatedMovies(): List<Movie> =
        api.getTopRatedMovies().results
            .map(MovieDto::toDomain)
}