package ua.kpi.comsys.ip8404.ui.movie

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import ua.kpi.comsys.ip8404.R
import java.io.InputStream
import java.util.*

class MovieAdapter (private val context: Context, private val data: MutableList<Movie>) : BaseAdapter() {
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        //var title: String = "", var year: String = "", var imdbID: String = "", var type: String = "",var poster: String = ""
        val view = inflater.inflate(R.layout.movie, parent, false)
        val titleFile = view.findViewById(R.id.id_title) as TextView
        val yearFile = view.findViewById(R.id.id_year) as TextView
        val typeFile = view.findViewById(R.id.id_type) as TextView
        val moviePoster = view.findViewById(R.id.id_image) as ImageView

        val movie = getItem(position) as Movie
        titleFile.text=movie.Title
        yearFile.text = movie.Year
        typeFile.text = movie.Type

        if(movie.Poster.isEmpty()){
            moviePoster.setImageResource(R.drawable.ic_action_2)
        } else {
            val in_image: InputStream = context.assets.open(movie.Poster.toLowerCase())
            val draw_image = Drawable.createFromStream(in_image, null)
            moviePoster.setImageDrawable(draw_image)
        }
        return view
    }
}