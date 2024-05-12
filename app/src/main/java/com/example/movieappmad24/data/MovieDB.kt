package com.example.movieappmad24.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieImage
import com.example.movieappmad24.worker.MovieDataSeedManager

@Database(
    entities = [Movie::class, MovieImage::class],
    version = 4,
    exportSchema = false
)
abstract class MovieDB : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        private var instance: MovieDB? = null

        fun getDatabase(context: Context): MovieDB = instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context): MovieDB {
            return Room.databaseBuilder(
                context.applicationContext,
                MovieDB::class.java,
                "movie_db"
            )
                .fallbackToDestructiveMigration()
                .addCallback(seedDatabaseCallback(context))
                .build()
        }

        private fun seedDatabaseCallback(context: Context): Callback {
            return object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    MovieDataSeedManager(context).seedDatabase()
                }
            }
        }
    }
}