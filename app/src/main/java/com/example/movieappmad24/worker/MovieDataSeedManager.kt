package com.example.movieappmad24.worker

import MovieDataSeederWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import android.content.Context

class MovieDataSeedManager(context: Context) {
    private val workManager = WorkManager.getInstance(context)

    fun seedDatabase() {
        val seedWorkRequest = OneTimeWorkRequestBuilder<MovieDataSeederWorker>().build()
        workManager.enqueue(seedWorkRequest)
    }
}
