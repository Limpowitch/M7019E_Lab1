package com.example.m7019e_lab1.data.workers

import android.content.Context
import androidx.work.*
import com.example.m7019e_lab1.models.Review
import com.example.m7019e_lab1.data.repository.MoviesRepositoryImpl
import com.example.m7019e_lab1.data.remote.client.RetrofitClient
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.coroutineScope
import java.io.IOException

class FetchMovieReviewsWorker(
    ctx: Context,
    params: WorkerParameters
) : CoroutineWorker(ctx, params) {
    companion object {
        const val KEY_MOVIE_ID       = "movie_id"
        const val UNIQUE_NAME_PREFIX = "fetch_reviews_"
        const val OUTPUT_REVIEWS_KEY = "output_reviews"
    }

    private val repo = MoviesRepositoryImpl(RetrofitClient.apiService)

    override suspend fun doWork(): Result = coroutineScope {
        val movieId = inputData.getLong(KEY_MOVIE_ID, -1L)
        if (movieId < 0) return@coroutineScope Result.failure()

        return@coroutineScope try {
            val reviews = repo.fetchMovieReviews(movieId)

            val moshi       = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            val adapter     = moshi.adapter(Review::class.java)
            val reviewsJson = reviews.map(adapter::toJson).toTypedArray()

            val data = workDataOf(OUTPUT_REVIEWS_KEY to reviewsJson)
            if (data.toByteArray().size > 10_000) {
                return@coroutineScope Result.failure() // A check to see if we have retrieved more information that WorkManager can handle
            }

            Result.success(data)
        } catch (e: IOException) {
            Result.retry()
        } catch (_: Throwable) {
            Result.failure()
        }
    }
}


