package com.example.movieappmad24.dependencyInjector

import android.content.Context
import com.example.movieappmad24.data.MovieRepo
import com.example.movieappmad24.data.MovieDB
import com.example.movieappmad24.viewmodels.ViewModelMoviesFactory

object Injector {
    private fun getMovieRepository(context: Context): MovieRepo =
        MovieRepo.getInstance(MovieDB.getDatabase(context.applicationContext).movieDao())

    fun provideMoviesViewModelFactory(context: Context): ViewModelMoviesFactory {
        val movieRepository = getMovieRepository(context)
        return ViewModelMoviesFactory(repository = movieRepository)
    }
}