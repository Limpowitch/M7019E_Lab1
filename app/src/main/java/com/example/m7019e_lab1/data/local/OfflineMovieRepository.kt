package com.example.m7019e_lab1.data.local

import kotlinx.coroutines.flow.Flow

class OfflineMovieRepository(private val movieDao: MovieDao) : MoviesRepository {
    override fun getAllMoviesStream(): Flow<List<Movie>> = movieDao.getAllMovies()

    override fun getMovieStream(id: Int): Flow<Movie?> = movieDao.getMovie(id)

    override suspend fun insertMovie(movie: Movie) = movieDao.insert(movie)

    override suspend fun deleteMovie(movie: Movie) = movieDao.delete(movie)

    override suspend fun updateMovie(movie: Movie) = movieDao.update(movie)
}