package com.example.m7019e_lab1.data.remote.mapper

import com.example.m7019e_lab1.data.remote.dto.MovieGridItemDto
import com.example.m7019e_lab1.models.MovieGridItem

fun MovieGridItemDto.toDomain(): MovieGridItem = MovieGridItem(
    id         = id,
    title      = title,
    posterPath = posterPath.orEmpty()
)