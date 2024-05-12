package com.example.movieappmad24.data

import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieImage
import com.example.movieappmad24.models.MovieWithImages
import kotlinx.coroutines.flow.Flow

class MovieRepo(private val movieDao: MovieDao) {

    suspend fun addMovie(movie: Movie) = movieDao.addMovie(movie)

    suspend fun addImages(movieImage: MovieImage) = movieDao.addImages(movieImage)

    suspend fun updateMovie(movie: Movie) = movieDao.update(movie)

    suspend fun deleteMovie(movie: Movie) = movieDao.delete(movie)

    fun getAll(): Flow<List<MovieWithImages>> = movieDao.getAll()

    fun getFavorites(): Flow<List<MovieWithImages>> = movieDao.getFavorites()

    fun getMovieById(id: Long): Flow<MovieWithImages?> = movieDao.get(id)

    companion object {
        @Volatile
        private var instance: MovieRepo? = null

        fun getInstance(dao: MovieDao): MovieRepo = instance ?: synchronized(this) {
            instance ?: MovieRepo(dao).also { instance = it }
        }
    }
}