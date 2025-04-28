package com.example.m7019e_lab1.data.remote.dto

import com.squareup.moshi.Json

data class MovieGridItemDto(
    @Json(name="id")           val id: Long,
    @Json(name="title")        val title: String,
    @Json(name="poster_path")  val posterPath: String?
)