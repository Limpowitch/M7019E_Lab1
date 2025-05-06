// data/remote/dto/MovieDetailsDto.kt
package com.example.m7019e_lab1.data.remote.dto

import com.squareup.moshi.Json

data class MovieDetailsDto(
    @Json(name="id")             val id: Long,
    @Json(name="title")          val title: String,
    @Json(name="poster_path")    val posterPath: String?,
    @Json(name="backdrop_path")  val backdropPath: String?,
    @Json(name="release_date")   val releaseDate: String?,
    @Json(name="overview")       val overview: String?,
    @Json(name="genres")         val genres: List<GenreDto>,
    @Json(name="imdb_id")        val imdbId: String?,
    @Json(name="homepage")       val homepageUrl: String?
)

data class GenreDto(
    val id: Int,
    val name: String
)