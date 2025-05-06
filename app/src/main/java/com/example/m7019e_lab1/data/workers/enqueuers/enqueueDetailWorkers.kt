package com.example.m7019e_lab1.data.workers.enqueuers

import android.content.Context
import androidx.work.*
import com.example.m7019e_lab1.data.workers.FetchMovieReviewsWorker
import com.example.m7019e_lab1.data.workers.FetchMovieVideosWorker

fun enqueueFetchMovieReviewsWork(context: Context, movieId: Long) {
    val req = OneTimeWorkRequestBuilder<FetchMovieReviewsWorker>()
        .setInputData(workDataOf(FetchMovieReviewsWorker.Companion.KEY_MOVIE_ID to movieId))
        .setConstraints(
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        )
        .build()

    WorkManager.getInstance(context)
        .enqueueUniqueWork(
            FetchMovieReviewsWorker.Companion.UNIQUE_NAME_PREFIX + movieId,
            ExistingWorkPolicy.REPLACE,
            req
        )
}

fun enqueueFetchMovieVideosWork(context: Context, movieId: Long) {
    val req = OneTimeWorkRequestBuilder<FetchMovieVideosWorker>()
        .setInputData(workDataOf(FetchMovieVideosWorker.Companion.KEY_MOVIE_ID to movieId))
        .setConstraints(
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        )
        .build()

    WorkManager.getInstance(context)
        .enqueueUniqueWork(
            FetchMovieVideosWorker.Companion.UNIQUE_NAME_PREFIX + movieId,
            ExistingWorkPolicy.REPLACE,
            req
        )
}
