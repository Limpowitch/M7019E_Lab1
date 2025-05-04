package com.example.m7019e_lab1.data.local

import androidx.room.TypeConverter


class Converters {

    @TypeConverter
    fun fromStringList(genres: List<String>): String =
        genres.joinToString(separator = ",")

    @TypeConverter
    fun toStringList(data: String): List<String> =
        if (data.isBlank()) emptyList() else data.split(",")
}