package com.example.m7019e_lab1.data.remote.mapper

import com.example.m7019e_lab1.data.remote.dto.VideoDto
import com.example.m7019e_lab1.models.Video

fun VideoDto.toDomain() = Video(
    id   = id,
    name = name,
    key  = key,
    site = site,
    type = type
)