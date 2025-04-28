package com.example.m7019e_lab1.data.remote.mapper

import com.example.m7019e_lab1.data.remote.dto.ReviewDto
import com.example.m7019e_lab1.models.Review

fun ReviewDto.toDomain(): Review = Review(
    id        = "${author}_${createdAt}", // or extract from DTO if it has an `id`
    author    = authorDetails.name?.takeIf { it.isNotBlank() } ?: author,
    content   = content,
    createdAt = createdAt,
    rating    = authorDetails.rating
)