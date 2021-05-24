package ua.kpi.comsys.ip8404.ui.movie

import android.content.Context
import android.graphics.drawable.Drawable
import com.beust.klaxon.Klaxon
import java.io.InputStream

class MovieReader(val Search: MutableList<Movie>)

fun read_movie_file(ctx: Context): MutableList<Movie> {
    val inputStream: InputStream = ctx.assets.open("MoviesList.txt")
    val jsonMovies = inputStream.bufferedReader().use { it.readText() }
    val movies = Klaxon().parse<MovieReader>(jsonMovies) ?: error("invalid movie JSON")
    for (movie in movies.Search) {
        val info: MovieInfo? = readimdbID(movie.imdbID, ctx)
        if (info == null) continue
        else movie.movieInfo = info
    }
    return movies.Search
}

fun readPoster(name: String, ctx: Context): Drawable? {
    val fileName: String = name.toLowerCase()
    if(fileName != ""){
    val pos: InputStream = ctx.assets.open(fileName)
    return Drawable.createFromStream(pos, null)}
    else {val pos: InputStream = ctx.assets.open("movie.png")
        return Drawable.createFromStream(pos, null)}
}

fun readimdbID(name: String, context: Context): MovieInfo? {
    val serialPattern = "^t{2}[0-9]{7}$".toRegex()
    if (!serialPattern.matches(name)) return null
    val filePath = "MovieInfo/$name.txt"
    val movieJSONInfo = context.assets.open(filePath)
        .bufferedReader().use { it.readText() }
    return Klaxon().parse<MovieInfo>(movieJSONInfo)
}