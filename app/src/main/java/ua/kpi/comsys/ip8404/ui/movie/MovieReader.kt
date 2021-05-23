package ua.kpi.comsys.ip8404.ui.movie

import android.content.Context
import com.beust.klaxon.Klaxon
import java.io.InputStream

class MovieReader(val Search: MutableList<Movie>)

fun read_movie_file(ctx: Context): MutableList<Movie> {

    val inputStream: InputStream = ctx.assets.open("MoviesList.txt")
    val jsonMovies = inputStream.bufferedReader().use { it.readText() }
    val movies = Klaxon().parse<MovieReader>(jsonMovies)
    if(movies !=null) {
        return movies.Search
    } else {return mutableListOf(Movie("", "", "", "", ""))}

}
