package ua.kpi.comsys.ip8404.ui.movie

class MovieInfo(
    var Title: String = "", var Year: String = "", var Rated: String = "",
    var Released: String = "", var Runtime: String = "", var Genre: String = "",
    var Director: String = "", var Writer: String = "", var Actors: String = "",
    var Plot: String = "",
    var Language: String = "", var Country: String = "", var Awards: String = "",
    var Poster: String = "", var imdbRating: String = "", var imdbVotes: String = "",
    var imdbID: String = "", var Type: String = "", var Production: String = ""
)

data class Movie(
    val Title: String = "", val Year: String = "", val imdbID: String = "",
    val Type: String = "", val Poster: String = "",
    var movieInfo: MovieInfo? = MovieInfo()
) {
    fun validate(movie: Movie): Movie {
        var Title = movie.Title
        var Year = movie.Year
        var imdbID = movie.imdbID
        var Type = movie.Type
        var Poster: String = movie.Poster
        val titleLength = 50
        val typeLength = 50
        if (movie.Title.length > titleLength) {
            Title = movie.Title.substring(0, titleLength)
            Title += "..."
        }
        val yearPattern = "^(1[89]|2[0123])[0-9]{2}$".toRegex()
        if (!yearPattern.matches(movie.Year)) {
            Year = "No year"
        }
        val imdbIDPattern = "^t{2}[0-9]{7}\$".toRegex()
        if (!imdbIDPattern.matches(movie.imdbID)) {
            imdbID = "No imdbID"
        }
        if (movie.Type.length > typeLength) {
            Type = movie.Type.substring(0, typeLength)
            Type += "..."
        }
        return Movie(Title, Year, imdbID, Type, Poster)
    }
}
