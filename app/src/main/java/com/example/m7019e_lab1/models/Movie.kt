package com.example.m7019e_lab1.models

data class Movie(
    var id: Long = 0L,
    var title: String,
    var posterPath: String,
    var backdropPath: String,
    var releaseDate: String,
    var overview: String,
    var genre: String,
    var imdb_id: String,
    var movie_homepage_url: String,

)