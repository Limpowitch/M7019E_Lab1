package com.example.m7019e_lab1.models

data class Review(
    val id: String,        // we’ll use the DTO’s `id` field or author+date key
    val author: String,
    val content: String,
    val createdAt: String,
    val rating: Int?
)