package com.example.movieappmad24.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieappmad24.data.MovieRepo

class ViewModelMoviesFactory(private val repository: MovieRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when {
        modelClass.isAssignableFrom(ViewModelHomeMovies::class.java) -> ViewModelHomeMovies(repository) as T
        modelClass.isAssignableFrom(ViewModelDetail::class.java) -> ViewModelDetail(repository) as T
        modelClass.isAssignableFrom(ViewModelWatchlistMovies::class.java) -> ViewModelWatchlistMovies(repository) as T
        else -> throw IllegalArgumentException("Unknown ViewModel class")
    }
}
