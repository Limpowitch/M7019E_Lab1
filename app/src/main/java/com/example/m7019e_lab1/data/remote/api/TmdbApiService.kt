//A collection of HTTP interface declarations
//Add additional API calls by declaring new GET/SET/etc functions -
// - mapped to its appropriate DTO

package com.example.m7019e_lab1.data.remote.api

import com.example.m7019e_lab1.data.remote.dto.MoviesResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApiService {

@GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("language") language: String = "en-US", // query options are outlined in TMDB api
        @Query("page") page: Int = 1
    ) : MoviesResponseDto
}
