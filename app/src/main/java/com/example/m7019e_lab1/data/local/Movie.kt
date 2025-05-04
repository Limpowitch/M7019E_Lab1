package com.example.m7019e_lab1.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey
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