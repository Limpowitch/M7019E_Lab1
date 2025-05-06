//A singleton object which predefines all network plumbing.
//So authorization, logging, an adaptor for 'moshi' to correctly map the @GET JSON data, etc

package com.example.m7019e_lab1.data.remote.client

import com.example.m7019e_lab1.data.remote.api.TmdbApiService
import com.example.m7019e_lab1.utils.SECRETS
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {
    private const val BASE_URL     = "https://api.themoviedb.org/3/"
    private const val BEARER_TOKEN = SECRETS.API_KEY

    private val authInterceptor = Interceptor { chain ->
        val newReq = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $BEARER_TOKEN")
            .addHeader("Accept", "application/json")
            .build()
        chain.proceed(newReq)
    }

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(logging)
        .build()

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val apiService: TmdbApiService by lazy {
        retrofit.create(TmdbApiService::class.java)
    }
}