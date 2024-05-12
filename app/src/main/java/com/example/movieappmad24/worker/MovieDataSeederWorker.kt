import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import android.content.Context
import com.example.movieappmad24.data.MovieDB
import com.example.movieappmad24.data.MovieRepo
import com.example.movieappmad24.models.MovieImage
import com.example.movieappmad24.models.getMovies

private const val TAG = "MovieDataSeederWorker"

class MovieDataSeederWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {
    private val repository = MovieRepo(movieDao = MovieDB.getDatabase(ctx).movieDao())

    override suspend fun doWork(): Result {
        return try {
            val movies = getMovies()
            movies.forEach { movie ->
                repository.addMovie(movie)
                movie.images.forEach { url ->
                    repository.addImages(MovieImage(movieDbId = movie.dbId, url = url))
                }
            }
            Result.success()
        } catch (throwable: Throwable) {
            Result.failure()
        }
    }
}
