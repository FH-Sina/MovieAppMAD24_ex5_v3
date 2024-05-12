package com.example.movieappmad24.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieImage(
    @PrimaryKey(autoGenerate = true) val movieImageId: Long = 0,
    val movieDbId: Long,
    val url: String
)