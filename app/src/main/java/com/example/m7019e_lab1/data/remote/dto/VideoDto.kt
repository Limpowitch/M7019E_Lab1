package com.example.m7019e_lab1.data.remote.dto

import com.squareup.moshi.Json

data class VideosResponseDto(
    val id: Long,
    val results: List<VideoDto>
)

data class VideoDto(
    val id: String,
    val name: String,
    val key: String,
    val site: String,
    val type: String,
    @Json(name="official") val isOfficial: Boolean
)