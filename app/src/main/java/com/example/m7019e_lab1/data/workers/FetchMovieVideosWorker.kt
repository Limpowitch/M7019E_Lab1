package com.example.m7019e_lab1.data.workers

import android.content.Context
import androidx.work.*
import com.example.m7019e_lab1.data.repository.MoviesRepositoryImpl
import com.example.m7019e_lab1.data.remote.client.RetrofitClient
import com.example.m7019e_lab1.models.Video
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.coroutineScope
import java.io.IOException

class FetchMovieVideosWorker(
    ctx: Context,
    params: WorkerParameters
) : CoroutineWorker(ctx, params) {
    companion object {
        const val KEY_MOVIE_ID       = "movie_id"
        const val UNIQUE_NAME_PREFIX = "fetch_videos_"
        const val OUTPUT_VIDEOS_KEY  = "output_videos"
    }

    private val repo = MoviesRepositoryImpl(RetrofitClient.apiService)

    override suspend fun doWork(): Result = coroutineScope {
        val movieId = inputData.getLong(KEY_MOVIE_ID, -1L)
        if (movieId < 0) return@coroutineScope Result.failure()

        return@coroutineScope try {
            val videos = repo.fetchMovieVideos(movieId)

            val moshi       = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            val adapter     = moshi.adapter(Video::class.java)
            val videosJson  = videos.map(adapter::toJson).toTypedArray()

            val data = workDataOf(OUTPUT_VIDEOS_KEY to videosJson)
            if (data.toByteArray().size > 10_000) { // A check to see if we have retrieved more information that WorkManager can handle
                return@coroutineScope Result.failure()
            }

            Result.success(data)
        } catch (e: IOException) {
            Result.retry()
        } catch (_: Throwable) {
            Result.failure()
        }
    }
}
