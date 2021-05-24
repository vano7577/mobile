package ua.kpi.comsys.ip8404.ui.movie

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import ua.kpi.comsys.ip8404.R
import java.io.InputStream

class MovieInfoActivity() : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_info)
        val imdbID: String = intent.getStringExtra("imdbID").toString()
        val movieInfo: MovieInfo? = readimdbID(imdbID, this)
        if (movieInfo != null) {
            findViewById<ImageView>(R.id.image).setImageDrawable(readPoster(movieInfo.Poster, this))
            findViewById<TextView>(R.id.title).text = movieInfo.Title
            findViewById<TextView>(R.id.year).text = movieInfo.Year
            findViewById<TextView>(R.id.genre).text = movieInfo.Genre
            findViewById<TextView>(R.id.director).text = movieInfo.Director
            findViewById<TextView>(R.id.actors).text = movieInfo.Actors
            findViewById<TextView>(R.id.country).text = movieInfo.Country
            findViewById<TextView>(R.id.language).text = movieInfo.Language
            findViewById<TextView>(R.id.production).text = movieInfo.Production
            findViewById<TextView>(R.id.released).text = movieInfo.Released
            findViewById<TextView>(R.id.runtime).text = movieInfo.Runtime
            findViewById<TextView>(R.id.awards).text = movieInfo.Awards
            findViewById<TextView>(R.id.rating).text = movieInfo.imdbRating
            findViewById<TextView>(R.id.plot).text = movieInfo.Plot
        }
    }
}