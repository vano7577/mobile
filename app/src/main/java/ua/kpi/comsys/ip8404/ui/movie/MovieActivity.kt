package ua.kpi.comsys.ip8404.ui.movie

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import ua.kpi.comsys.ip8404.R

class MovieActivity : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_movie, container, false)
       // val view: View = root.findViewById(R.id.id_listView)
        context?.let {addMovies(root,it)}
        return root
    }
}
fun addMovies(view: View,ctx : Context){
    val listView: ListView = view.findViewById(R.id.id_listView)
    val arrMovie : MutableList<Movie> = read_movie_file(ctx)
    listView.adapter = MovieAdapter(ctx, arrMovie)
}