package com.example.movieappmad24.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappmad24.data.MovieRepo
import com.example.movieappmad24.otherInterfaces.MoviesViewModel
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieWithImages
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class ViewModelHomeMovies(private val repository: MovieRepo) : ViewModel(), MoviesViewModel {
    private val _movies = MutableStateFlow(emptyList<MovieWithImages>())
    override val movies: StateFlow<List<MovieWithImages>> = _movies.asStateFlow()

    init {
        loadMovies()
    }

    private fun loadMovies() {
        viewModelScope.launch {
            repository.getAll().distinctUntilChanged().collect { updatedMovies ->
                _movies.value = updatedMovies
                Log.i("HomeViewModel", "Movies updated")
            }
        }
    }

    override fun updateFavorite(movie: Movie) {
        movie.isFavorite = !movie.isFavorite
        viewModelScope.launch {
            repository.updateMovie(movie)
            Log.i("HomeViewModel", "Favorite status updated for movie: ${movie.title}")
        }
    }
}
