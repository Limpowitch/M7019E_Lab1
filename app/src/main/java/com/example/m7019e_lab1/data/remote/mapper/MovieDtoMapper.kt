//Maps DTO to domain-level data. Allows the 'frontend' of the program to access retrieved data

package com.example.m7019e_lab1.data.remote.mapper

import com.example.m7019e_lab1.data.remote.dto.MovieDto
import com.example.m7019e_lab1.models.Movie


fun MovieDto.toDomain(): Movie = Movie(
    id                  = id,
    title               = title,
    posterPath          = posterPath.orEmpty(),
    backdropPath        = backdropPath.orEmpty(),
    releaseDate         = releaseDate.orEmpty(),
    overview            = overview.orEmpty(),
    genre               = emptyList(),
    imdb_id             = "",
    movie_homepage_url  = ""
)
