package com.example.m7019e_lab1.data.remote.dto

import com.squareup.moshi.Json

data class ReviewsResponseDto(
    val id: Long,
    val page: Int,
    val results: List<ReviewDto>,
    @Json(name="total_pages") val totalPages: Int,
    @Json(name="total_results") val totalResults: Int
)

data class ReviewDto(
    val author: String,
    @Json(name="content") val content: String,
    @Json(name="created_at") val createdAt: String,
    @Json(name="author_details") val authorDetails: AuthorDetailsDto
)

data class AuthorDetailsDto(
    val name: String?,
    val username: String,
    @Json(name="avatar_path") val avatarPath: String?,
    val rating: Int?
)