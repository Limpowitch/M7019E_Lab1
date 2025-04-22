//Data transfer object for retrieving a list of movies, such as 'top_rated'

package com.example.m7019e_lab1.data.remote.dto

import com.squareup.moshi.Json

data class MoviesResponseDto (
    val page: Int,
    val results: List<MovieDto>,
    @Json(name="total_pages") val totalPages: Int,
    @Json(name="total_results") val totalResults: Int
)