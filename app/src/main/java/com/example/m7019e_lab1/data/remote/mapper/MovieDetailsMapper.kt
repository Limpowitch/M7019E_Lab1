// data/remote/mapper/MovieDetailsMapper.kt
package com.example.m7019e_lab1.data.remote.mapper

import com.example.m7019e_lab1.data.remote.dto.MovieDetailsDto
import com.example.m7019e_lab1.models.Movie

fun MovieDetailsDto.toDomain(): Movie = Movie(
    id            = id,
    title         = title,
    posterPath    = posterPath.orEmpty(),
    backdropPath  = backdropPath.orEmpty(),
    releaseDate   = releaseDate.orEmpty(),
    overview      = overview.orEmpty(),
    genres        = genres.map { it.name },
    imdbId        = imdbId.orEmpty(),
    homepageUrl   = homepageUrl.orEmpty()
)
