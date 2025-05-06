package com.example.m7019e_lab1.data.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.m7019e_lab1.data.local.MovieDatabase
import com.example.m7019e_lab1.data.local.toEntity
import com.example.m7019e_lab1.data.remote.client.RetrofitClient
import com.example.m7019e_lab1.data.repository.MoviesRepositoryImpl
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay


class FetchMoviesWorker(
    context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params){

    companion object {
        const val KEY_CATEGORY = "category"
        const val CAT_TOP = "TOP"
        const val CAT_POPULAR = "POPULAR"
    }

    private val dao = MovieDatabase
        .getDatabase(context)
        .movieDao()

    private val remoteRepo = MoviesRepositoryImpl(RetrofitClient.apiService)

    override suspend fun doWork(): Result = coroutineScope {
        val cat = inputData.getString(KEY_CATEGORY) ?: return@coroutineScope Result.failure()

        dao.deleteExceptCategory(cat)

        // 2) fetch remote
        val movies = when(cat) {
            CAT_POPULAR -> remoteRepo.fetchPopularMovies()
            CAT_TOP     -> remoteRepo.fetchTopRatedMovies()
            else        -> throw IllegalArgumentException("Unknown category: $cat")
        }

       val entities = movies
         .mapIndexed { idx, m ->
               m.toEntity().copy(category = cat, retrieveIndex = idx)
             }
       dao.insertAll(entities)

        Result.success()
    }

}