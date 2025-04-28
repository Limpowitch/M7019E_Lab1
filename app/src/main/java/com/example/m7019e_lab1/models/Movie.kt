//Domain level data model

package com.example.m7019e_lab1.models

data class Movie(
    val id: Long,
    val title: String,
    val posterPath: String,
    val backdropPath: String,
    val releaseDate: String,
    val overview: String,
    val genres: List<String>,
    val imdbId: String,
    val homepageUrl: String
)