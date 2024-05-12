package com.example.movieappmad24.viewmodels

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

class ViewModelWatchlistMovies(private val repository: MovieRepo) : ViewModel(), MoviesViewModel {
    private val _movies = MutableStateFlow(emptyList<MovieWithImages>())
    override val movies: StateFlow<List<MovieWithImages>> = _movies.asStateFlow()

    init {
        loadFavoriteMovies()
    }

    private fun loadFavoriteMovies() {
        viewModelScope.launch {
            repository.getFavorites().distinctUntilChanged().collect { favoriteMovies ->
                _movies.value = favoriteMovies
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