package ua.kpi.comsys.ip8404.ui.movie

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import ua.kpi.comsys.ip8404.R

class MovieInfoActivity() : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_info)
        val imdbID: String = intent.getStringExtra("imdbID").toString()
        val mainLayout = findViewById<View>(R.id.mainLayout)
        val loadingProp = findViewById<View>(R.id.loadingPropInfo)
        loadingProp.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch { fillInfo(mainLayout, imdbID, loadingProp) }
    }
}

@SuppressLint("SetTextI18n")
suspend fun fillInfo(mainLayout: View, imdbID: String, loadingProp: View): Boolean {
    val movieInfo = fetchMovieInfoFromWeb(imdbID)
    val bitmapImage = movieInfo?.let { getBitmapFromURL(it.Poster) }

    withContext(Dispatchers.Main) {
        mainLayout.visibility = View.VISIBLE
        loadingProp.visibility = View.GONE
        if (movieInfo != null) {
            mainLayout.findViewById<ImageView>(R.id.image)
                .setImageBitmap(bitmapImage)
            mainLayout.findViewById<TextView>(R.id.title).text = "Title: " + movieInfo.Title
            mainLayout.findViewById<TextView>(R.id.year).text = "Year: " + movieInfo.Year
            mainLayout.findViewById<TextView>(R.id.genre).text = "Genre: " +  movieInfo.Genre
            mainLayout.findViewById<TextView>(R.id.director).text = "Director: " + movieInfo.Director
            mainLayout.findViewById<TextView>(R.id.actors).text = "Actors: "+ movieInfo.Actors
            mainLayout.findViewById<TextView>(R.id.country).text = "Country: " + movieInfo.Country
            mainLayout.findViewById<TextView>(R.id.language).text = "Language: " + movieInfo.Language
            mainLayout.findViewById<TextView>(R.id.production).text = "Production" +  movieInfo.Production
            mainLayout.findViewById<TextView>(R.id.released).text = "Released: "+ movieInfo.Released
            mainLayout.findViewById<TextView>(R.id.runtime).text = "Runtime: "+movieInfo.Runtime
            mainLayout.findViewById<TextView>(R.id.awards).text = "Awards: "+movieInfo.Awards
            mainLayout.findViewById<TextView>(R.id.rating).text = "Rating: " + movieInfo.imdbRating
            mainLayout.findViewById<TextView>(R.id.plot).text = "Plot: "+ movieInfo.Plot
        }
    }
    return true
}
