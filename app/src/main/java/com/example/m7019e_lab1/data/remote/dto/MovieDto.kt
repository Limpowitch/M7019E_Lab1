//Data transfer object for a specific movie

package com.example.m7019e_lab1.data.remote.dto

import com.squareup.moshi.Json

data class MovieDto(
    var id: Long,
    @Json(name = "title")               val title: String,
    @Json(name = "poster_path")         val posterPath: String? = null,
    @Json(name = "backdrop_path")       val backdropPath: String?,
    @Json(name = "release_date")        val releaseDate: String?,
    @Json(name = "overview")            val overview: String?,
    @Json(name = "genre")               val genre: String?,
    @Json(name = "imdb_id")             val imdb_id: String?,
    @Json(name = "movie_homepage_url")  val movie_homepage_url: String?,
    )