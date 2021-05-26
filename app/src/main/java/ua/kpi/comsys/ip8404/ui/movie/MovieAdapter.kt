package ua.kpi.comsys.ip8404.ui.movie

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ua.kpi.comsys.ip8404.R
import java.util.*

class MovieAdapter(private val context: Context, var data: MutableList<Movie>) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(), Filterable {
    var dataFiltered = mutableListOf<Movie>()

    init {
        dataFiltered = data
    }

    class MovieViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val relativeLayoutView = view.findViewById(R.id.relLay) as View
        val titleTextView = view.findViewById(R.id.id_title) as TextView
        val yearTextView = view.findViewById(R.id.id_year) as TextView
        val typeTextView = view.findViewById(R.id.id_type) as TextView
        val PosterView = view.findViewById(R.id.id_image) as ImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val bookItemLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie, parent, false)
        return MovieViewHolder(bookItemLayout)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        var movie = Movie()
        movie = movie.validate(dataFiltered[position])

        holder.relativeLayoutView.setOnClickListener {
            val intent = Intent(context, MovieInfoActivity::class.java)
            intent.putExtra("imdbID", movie.imdbID)
            context.startActivity(intent)
        }
        holder.titleTextView.text = (movie.Title)
        holder.yearTextView.text = movie.Year
        holder.typeTextView.text = (movie.Type)
        if (movie.Poster == "N/A" || movie.Poster == "") holder.PosterView.setImageBitmap(
            BitmapFactory.decodeStream(context.assets.open("movie.png"))
        )
        else {
            CoroutineScope(IO).launch {
                val bitmap = getBitmapFromURL(movie.Poster)
                withContext(Dispatchers.Main) {
                    holder.PosterView.setPadding(-50)
                    holder.PosterView.setImageBitmap(bitmap)
                }
            }
        }
    }

    override fun getFilter(): Filter {

        return object : Filter() {

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()

                if (charSearch.isEmpty()) {
                    dataFiltered = data
                } else {
                    val resultList = mutableListOf<Movie>()
                    for (movie in data) {
                        if (movie.Title.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT))
                        ) {
                            resultList.add(movie)
                        }
                    }
                    dataFiltered = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = dataFiltered
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                dataFiltered = results?.values as MutableList<Movie>
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int = dataFiltered.size
}