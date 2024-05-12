package com.example.movieappmad24.otherInterfaces

import kotlinx.coroutines.flow.StateFlow
import com.example.movieappmad24.models.MovieWithImages


interface MoviesViewModel : ViewModelFunctions {
    val movies: StateFlow<List<MovieWithImages>>
}