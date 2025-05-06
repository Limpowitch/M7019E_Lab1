package com.example.m7019e_lab1.data.repository

import com.example.m7019e_lab1.data.local.toDomain
import com.example.m7019e_lab1.data.local.toEntity
import com.example.m7019e_lab1.models.Movie
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.coroutineScope


class CachedMoviesRepository(
    private val remote: MoviesRepository,    // TMDB‐backed repo
    private val local: com.example.m7019e_lab1.data.local.MoviesRepository // your Room DAO wrapper
) : MoviesRepository {

    override suspend fun fetchTopRatedMovies(): List<Movie> = coroutineScope {
        // 1) check the cache
        val cached = local.getAllMoviesStream().first()
        if (cached.isNotEmpty()) {
            return@coroutineScope cached.map { it.toDomain() }
        }

        // 2) otherwise hit TMDB
        val movies = remote.fetchTopRatedMovies() // now returns full List<Movie>
        // 3) persist them
        movies.forEach { local.insertMovie(it.toEntity()) }
        return@coroutineScope movies
    }

    override suspend fun fetchPopularMovies(): List<Movie> = coroutineScope {
        // 1) check the cache
        val cached = local.getAllMoviesStream().first()
        if (cached.isNotEmpty()) {
            return@coroutineScope cached.map { it.toDomain() }
        }

        // 2) otherwise hit TMDB
        val movies = remote.fetchPopularMovies() // now returns full List<Movie>
        // 3) persist them
        movies.forEach { local.insertMovie(it.toEntity()) }
        return@coroutineScope movies
    }

    // delegate everything else straight to the remote:
    override suspend fun fetchMovieDetails(id: Long)   = remote.fetchMovieDetails(id)
    override suspend fun fetchMovieReviews(id: Long)   = remote.fetchMovieReviews(id)
    override suspend fun fetchMovieVideos(id: Long)    = remote.fetchMovieVideos(id)
}