package ua.kpi.comsys.ip8404.ui.movie

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.beust.klaxon.Klaxon
import java.io.InputStream
import java.net.URL
import java.net.URLConnection

class WebMoviesObj(
    val Search: MutableList<Movie> = mutableListOf<Movie>(),
    val error: String = "",
    val page: String = "",
    val total: String = ""
)

data class WebImageInfo(
    val id: Int = 0,
    val pageURL: String = "",
    val type: String = "",
    val tags: String = "",
    val previewURL: String = "",
    val previewWidth: Int = 0,
    val previewHeight: Int = 0,
    val webformatURL: String = "",
    val webformatWidth: Int = 0,
    val webformatHeight: Int = 0,
    val largeImageURL: String = "",
    val imageWidth: Int = 0,
    val imageHeight: Int = 0,
    val imageSize: Int = 0,
    val views: Int = 0,
    val downloads: Int = 0,
    val favorites: Int = 0,
    val likes: Int = 0,
    val comments: Int = 0,
    val user_id: Int = 0,
    val user: String = "",
    val userImageURL: String = ""
) {}

class WebImageResponse(val total: Int, val totalHits: Int, val hits: MutableList<WebImageInfo>) {}

fun fetchMoviesFromWeb(searchText: String): WebMoviesObj? {
    if (searchText.length < 3 || containsSpecChars(searchText)) return null


    val url = "http://www.omdbapi.com/?apikey=7e9fe69e&s=" + searchText + "&page=1"
    val response = createURLConnection(url) ?: return null
    if (response[13] == 'F') return null
    return Klaxon().parse<WebMoviesObj>(response) ?: error("invalid movie JSON")
}

fun fetchMovieInfoFromWeb(imdbID: String): MovieInfo? {

    val serialPattern = "^t{2}[0-9]{7}\$".toRegex()
    if (!serialPattern.matches(imdbID)) return null

    val url = "http://www.omdbapi.com/?apikey=7e9fe69e&i=" + imdbID
    val response = createURLConnection(url) ?: return null
    if (response[13] == 'F') return null
    return Klaxon().parse<MovieInfo>(response) ?: error("invalid imdbID movieInfo JSON")
}

fun fetchImageInfoFromWeb(REQUEST: String, COUNT: Int): WebImageResponse? {
    val API_KEY = "19193969-87191e5db266905fe8936d565"
    val url = "https://pixabay.com/api/?key=$API_KEY&q=$REQUEST&image_type=photo&per_page=$COUNT"
    val response = createURLConnection(url) ?: return null
    val imageResponse =
        Klaxon().parse<WebImageResponse>(response) ?: error("invalid WebImageResponse JSON")
    return imageResponse
}

fun getBitmapFromURL(src: String): Bitmap? {
    val connection = URL(src).openConnection()
    connection.doInput = true
    val input: InputStream = connection.getInputStream()
    return BitmapFactory.decodeStream(input)
}

private fun createURLConnection(url: String): String? {
    val openConnection: URLConnection = URL(url).openConnection()
    openConnection.addRequestProperty(
        "User-Agent",
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.150 Safari/537.36"
    )
    val response = openConnection.getInputStream().bufferedReader().use { it.readText() }
    return response
}

fun containsSpecChars(string: String): Boolean {
    val pattern = Regex("""[^a-zA-Z .,]""")
    return pattern.containsMatchIn(string)
}
