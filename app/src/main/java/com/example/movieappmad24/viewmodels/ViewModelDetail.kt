package com.example.movieappmad24.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappmad24.data.MovieRepo
import com.example.movieappmad24.otherInterfaces.ViewModelFunctions
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieWithImages
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ViewModelDetail(private val repository: MovieRepo) : ViewModel(), ViewModelFunctions {
    private val _movie = MutableStateFlow<MovieWithImages?>(null)
    val movie: StateFlow<MovieWithImages?> = _movie.asStateFlow()

    fun getMovieById(movieId: String) {
        val id = movieId.toLongOrNull()
        id?.let { nonNullId ->
            viewModelScope.launch {
                repository.getMovieById(nonNullId).collect { movieWithImages ->
                    _movie.value = movieWithImages
                }
            }
        }
    }

    override fun updateFavorite(movie: Movie) {
        movie.isFavorite = !movie.isFavorite
        viewModelScope.launch {
            repository.updateMovie(movie)
        }
    }
}
