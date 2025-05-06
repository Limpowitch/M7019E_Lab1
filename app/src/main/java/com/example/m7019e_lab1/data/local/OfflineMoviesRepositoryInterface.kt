package com.example.m7019e_lab1.data.local

import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    fun getAllMoviesStream(): Flow<List<Movie>>

    fun getMovieStream(id: Int): Flow<Movie?>

    suspend fun insertMovie(movie: Movie)

    suspend fun deleteMovie(movie: Movie)

    suspend fun updateMovie(movie: Movie)
}