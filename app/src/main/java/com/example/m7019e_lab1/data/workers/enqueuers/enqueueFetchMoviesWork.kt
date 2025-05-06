package com.example.m7019e_lab1.data.workers.enqueuers

import android.content.Context
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.m7019e_lab1.data.workers.FetchMoviesWorker

fun enqueueFetchMoviesWork(context: Context, category: String) {
    val wm = WorkManager.getInstance(context)

    wm.cancelAllWorkByTag("FETCH_MOVIES")

    val request = OneTimeWorkRequestBuilder<FetchMoviesWorker>()
        .setInputData(workDataOf(FetchMoviesWorker.Companion.KEY_CATEGORY to category))
        .setConstraints(
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        )
        .addTag("FETCH_MOVIES")
        .build()

    wm.enqueue(request)
}