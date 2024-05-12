package com.example.movieappmad24.widgets


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieWithImages
import com.example.movieappmad24.navigation.Screen
import com.example.movieappmad24.otherInterfaces.MoviesViewModel

@Composable
fun MovieList(
    movies: List<MovieWithImages>,
    modifier: Modifier,
    navController: NavController,
    viewModel: MoviesViewModel
) {
    LazyColumn(modifier = modifier) {
        if (movies.isNotEmpty()) {
            items(movies) { movieWithImages ->
                MovieRow(
                    movieWithImages = movieWithImages,
                    onFavoriteClick = { viewModel.updateFavorite(movieWithImages.movie) },
                    onItemClick = {
                        navController.navigate(
                            route = Screen.DetailScreen.withId(id = movieWithImages.movie.dbId.toString())
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun MovieRow(
    movieWithImages: MovieWithImages,
    modifier: Modifier = Modifier,
    onFavoriteClick: (String) -> Unit = {},
    onItemClick: (String) -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable { onItemClick(movieWithImages.movie.id) },
        shape = ShapeDefaults.Large,
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Column {
            MovieCardHeader(
                imageUrl = movieWithImages.movieImages.firstOrNull()?.url ?: "",
                movieWithImages = movieWithImages,
                onFavoriteClick = { onFavoriteClick(movieWithImages.movie.id) }
            )

            MovieDetails(modifier = modifier.padding(12.dp), movie = movieWithImages.movie)
        }
    }
}

@Composable
fun MovieCardHeader(
    imageUrl: String,
    movieWithImages: MovieWithImages,
    onFavoriteClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        MovieImage(imageUrl)

        FavoriteIcon(movieWithImages = movieWithImages, onFavoriteClick = onFavoriteClick)
    }
}

@Composable
fun MovieImage(imageUrl: String) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentScale = ContentScale.Crop,
        contentDescription = "movie poster",
        loading = { CircularProgressIndicator() }
    )
}

@Composable
fun FavoriteIcon(
    movieWithImages: MovieWithImages,
    onFavoriteClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        Icon(
            modifier = Modifier.clickable { onFavoriteClick() },
            tint = Color.Red,
            imageVector = if (movieWithImages.movie.isFavorite) Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = "Add to favorites"
        )
    }
}

@Composable
fun MovieDetails(modifier: Modifier, movie: Movie) {
    var showDetails by remember { mutableStateOf(false) }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = movie.title)
        Icon(
            modifier = Modifier.clickable { showDetails = !showDetails },
            imageVector = if (showDetails) Icons.Filled.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
            contentDescription = "show more"
        )
    }

    AnimatedVisibility(
        visible = showDetails,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Column(modifier = modifier) {
            Text("Director: ${movie.director}", style = MaterialTheme.typography.bodySmall)
            Text("Released: ${movie.year}", style = MaterialTheme.typography.bodySmall)
            Text("Genre: ${movie.genre}", style = MaterialTheme.typography.bodySmall)
            Text("Actors: ${movie.actors}", style = MaterialTheme.typography.bodySmall)
            Text("Rating: ${movie.rating}", style = MaterialTheme.typography.bodySmall)

            Divider(modifier = Modifier.padding(3.dp))

            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.DarkGray, fontSize = 13.sp)) {
                    append("Plot: ")
                }
                withStyle(style = SpanStyle(color = Color.DarkGray, fontSize = 13.sp, fontWeight = FontWeight.Normal)) {
                    append(movie.plot)
                }
            })
        }
    }
}

@Composable
fun HorizontalScrollableImageView(movieWithImages: MovieWithImages) {
    LazyRow {
        items(movieWithImages.movieImages) { image ->
            Card(
                modifier = Modifier
                    .padding(12.dp)
                    .size(240.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(image.url)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Movie poster",
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}
