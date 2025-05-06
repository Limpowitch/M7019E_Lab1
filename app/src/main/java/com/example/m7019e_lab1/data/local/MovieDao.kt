package com.example.m7019e_lab1.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)

    @Update
    suspend fun update(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)

    @Query("SELECT * from movies WHERE id = :id")
    fun getMovie(id: Int): Flow<Movie>

    @Query("SELECT * from movies ORDER BY retrieveIndex ASC")
    fun getAllMovies(): Flow<List<Movie>>

    @Query("DELETE FROM movies")
    suspend fun deleteAllMovies()

    @Query("SELECT * FROM movies WHERE category = :cat ORDER BY retrieveIndex ASC")
    fun getMoviesForCategory(cat: String): Flow<List<Movie>>

    @Query("DELETE FROM movies WHERE category = :cat")
    suspend fun deleteMoviesForCategory(cat: String)

    @Query("DELETE FROM movies WHERE category != :cat")
    suspend fun deleteExceptCategory(cat: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<Movie>)

}