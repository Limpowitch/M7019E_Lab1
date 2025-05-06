//Domain level implementation of function to retrieve data from TMDB api
//Likewise makes sure to map the raw DTOs against our domain models

package com.example.m7019e_lab1.data.repository

import com.example.m7019e_lab1.data.remote.api.TmdbApiService
import com.example.m7019e_lab1.data.remote.dto.MovieGridItemDto
import com.example.m7019e_lab1.data.remote.dto.ReviewDto
import com.example.m7019e_lab1.data.remote.dto.VideoDto
import com.example.m7019e_lab1.data.remote.mapper.toDomain
import com.example.m7019e_lab1.data.workers.FetchMoviesWorker
import com.example.m7019e_lab1.models.Movie
import com.example.m7019e_lab1.models.Review
import com.example.m7019e_lab1.models.Video
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class MoviesRepositoryImpl(
    private val api: TmdbApiService
) : MoviesRepository {

    override suspend fun fetchTopRatedMovies(): List<Movie> {
        val gridDtos = api.getTopRatedMovies().results

        return coroutineScope {
            gridDtos
                .mapIndexed { index, dto ->
                    async {
                        // fetch full details
                        api.getMovieDetails(dto.id)
                            .toDomain(category = FetchMoviesWorker.CAT_TOP)
                            // now tack on the index
                            .copy(retrieveIndex = index)
                    }
                }
                .awaitAll()
        }
    }

    override suspend fun fetchPopularMovies(): List<Movie> {
        val gridDtos = api.getPopularMovies().results

        return coroutineScope {
            gridDtos
                .mapIndexed { index, dto ->
                    async {
                        // fetch full details
                        api.getMovieDetails(dto.id)
                            .toDomain(category = FetchMoviesWorker.CAT_POPULAR)
                            // now tack on the index
                            .copy(retrieveIndex = index)
                    }
                }
                .awaitAll()
        }
    }

    override suspend fun fetchMovieDetails(id: Long): Movie =
        api.getMovieDetails(id)
            .toDomain()


    override suspend fun fetchMovieReviews(id: Long): List<Review> =
        api.getMovieReviews(id)
            .results
            .map(ReviewDto::toDomain)

    override suspend fun fetchMovieVideos(id: Long): List<Video> =
        api.getMovieVideos(id)
            .results
            .filter { it.site == "YouTube" }
            .map(VideoDto::toDomain)
            .take(3)
}