package com.example.movieappmad24.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Delete
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieImage
import com.example.movieappmad24.models.MovieWithImages
import kotlinx.coroutines.flow.Flow


@Dao
interface MovieDao {
    @Insert
    suspend fun addMovie(movie: Movie)

    @Insert
    suspend fun addImages(movieImage: MovieImage)

    @Update
    suspend fun update(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)


    @Transaction
    @Query("SELECT * FROM movie")
    fun getAll(): Flow<List<MovieWithImages>>

    @Transaction
    @Query("SELECT * FROM movie WHERE dbId=:id")
    fun get(id: Long): Flow<MovieWithImages>


    @Transaction
    @Query("SELECT * FROM movie WHERE isFavorite=1")
    fun getFavorites(): Flow<List<MovieWithImages>>
}
