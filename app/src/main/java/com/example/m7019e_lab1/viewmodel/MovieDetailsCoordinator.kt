// A helper class which coordinates the retrieval of base movie information from cache, and videos/reviews from remote

package com.example.m7019e_lab1.viewmodel

import android.content.Context
import androidx.work.WorkManager
import androidx.work.WorkInfo
import com.example.m7019e_lab1.data.workers.FetchMovieReviewsWorker
import com.example.m7019e_lab1.data.workers.FetchMovieVideosWorker
import com.example.m7019e_lab1.data.local.MovieDatabase
import com.example.m7019e_lab1.data.local.toDomain
import com.example.m7019e_lab1.data.workers.enqueuers.enqueueFetchMovieReviewsWork
import com.example.m7019e_lab1.data.workers.enqueuers.enqueueFetchMovieVideosWork
import com.example.m7019e_lab1.models.Movie
import com.example.m7019e_lab1.models.Review
import com.example.m7019e_lab1.models.Video
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class MovieDetailsCoordinator(
    private val context: Context,
    private val scope: CoroutineScope,
    private val selectedMovie: MutableStateFlow<Movie?>,
    private val reviews: MutableStateFlow<List<Review>>,
    private val videos: MutableStateFlow<List<Video>>,
    private val errorMessage: MutableStateFlow<String?>
) {
    private val movieDao = MovieDatabase.getDatabase(context).movieDao()
    private val workManager = WorkManager.getInstance(context)
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val reviewAdapter = moshi.adapter(Review::class.java)
    private val videoAdapter  = moshi.adapter(Video::class.java)

    fun loadDetails(id: Long) {
        loadBaseDetails(id)
        resetState()
        enqueueWorkers(id)
        observeResults(id)
    }

    private fun loadBaseDetails(id: Long) = scope.launch {              // Base details are saved in cache, so we retrieve directly from local
        val entity = movieDao.getMovie(id.toInt()).firstOrNull()
        selectedMovie.value = entity?.toDomain()
    }

    private fun resetState() {
        reviews.value      = emptyList()
        videos.value       = emptyList()
        errorMessage.value = null
    }

    private fun enqueueWorkers(id: Long) {                             // Defined in enqueueDetailsWorkers
        enqueueFetchMovieReviewsWork(context, id)
        enqueueFetchMovieVideosWork(context, id)
    }

    private fun observeResults(id: Long) {
        // Reviews
        workManager
            .getWorkInfosForUniqueWorkLiveData(FetchMovieReviewsWorker.UNIQUE_NAME_PREFIX + id)
            .observeForever { infos ->
                infos.firstOrNull { it.state == WorkInfo.State.SUCCEEDED }?.let {
                    val arr = it.outputData
                        .getStringArray(FetchMovieReviewsWorker.OUTPUT_REVIEWS_KEY)
                        ?: emptyArray()
                    reviews.value = arr.mapNotNull(reviewAdapter::fromJson)
                }
                if (infos.any { it.state == WorkInfo.State.FAILED }) {
                    errorMessage.value = "Could not load reviews."
                }
            }

        // Videos
        workManager
            .getWorkInfosForUniqueWorkLiveData(FetchMovieVideosWorker.UNIQUE_NAME_PREFIX + id)
            .observeForever { infos ->
                infos.firstOrNull { it.state == WorkInfo.State.SUCCEEDED }?.let {
                    val arr = it.outputData
                        .getStringArray(FetchMovieVideosWorker.OUTPUT_VIDEOS_KEY)
                        ?: emptyArray()
                    videos.value = arr.mapNotNull(videoAdapter::fromJson)
                }
                if (infos.any { it.state == WorkInfo.State.FAILED }) {
                    errorMessage.value = "Could not load videos."
                }
            }
    }
}
