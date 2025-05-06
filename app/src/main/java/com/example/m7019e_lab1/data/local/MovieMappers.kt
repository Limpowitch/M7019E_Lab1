package com.example.m7019e_lab1.data.local

import com.example.m7019e_lab1.models.Movie as DomainMovie

fun DomainMovie.toEntity() = Movie(
    id           = id,
    title        = title,
    posterPath   = posterPath,
    backdropPath = backdropPath,
    releaseDate  = releaseDate,
    overview     = overview,
    genres       = genres,
    imdbId       = imdbId,
    homepageUrl  = homepageUrl,
    retrieveIndex = retrieveIndex,
    category = category
)

fun Movie.toDomain() = DomainMovie(
    id           = id,
    title        = title,
    posterPath   = posterPath,
    backdropPath = backdropPath,
    releaseDate  = releaseDate,
    overview     = overview,
    genres       = genres,
    imdbId       = imdbId,
    homepageUrl  = homepageUrl,
    retrieveIndex = retrieveIndex,
    category = category

)