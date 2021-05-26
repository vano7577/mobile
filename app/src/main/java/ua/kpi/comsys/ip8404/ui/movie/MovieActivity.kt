package ua.kpi.comsys.ip8404.ui.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.tsuryo.swipeablerv.SwipeLeftRightCallback
import com.tsuryo.swipeablerv.SwipeableRecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ua.kpi.comsys.ip8404.R

class MovieActivity : Fragment() {
    var adapter: MovieAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie, container, false)
        val recycler: SwipeableRecyclerView = view.findViewById(R.id.moviesRecylerView)
        val loManager = LinearLayoutManager(context)
        val data = mutableListOf<Movie>()
        adapter = context?.let { MovieAdapter(it, data) }
        recycler.adapter = adapter
        recycler.layoutManager = loManager
        recycler.setHasFixedSize(true)

        recycler.setListener(object : SwipeLeftRightCallback.Listener {
            override fun onSwipedLeft(position: Int) {
                adapter?.data?.removeAt(position)
                adapter?.notifyItemRemoved(position)
            }

            override fun onSwipedRight(position: Int) {
                TODO("Not yet implemented")
            }
        })

        val searchBar = view.findViewById<SearchView>(R.id.searchBar)
        initSearchBarEvents(searchBar)

        val addButton: ImageButton = view.findViewById(R.id.addButton)
        addButton.setOnClickListener {
            val i = Intent(context, AddMovieActivity::class.java)
            startActivityForResult(i, 1)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val title = data?.getStringExtra("Title")
        val year = data?.getStringExtra("Year")
        val type = data?.getStringExtra("Type")

        if (title == null || year == null || type == null) {
            return
        }

        val movie = Movie(title, year, "", type, "", null)

        adapter?.data?.add(movie)
        adapter?.data?.let { adapter?.notifyItemInserted(it.size) }
    }
}

fun MovieActivity.initSearchBarEvents(searchBar: SearchView) {

    searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

        override fun onQueryTextSubmit(query: String?): Boolean {
            return query != null
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            if (newText == null || newText.length < 3) return false
            val loadingProp = view?.findViewById<View>(R.id.loadingProp)
            loadingProp?.visibility = View.VISIBLE
            CoroutineScope(IO).launch {
                val movies = fetchMoviesFromWeb(newText)?.Search
                changeRecyclerRows(loadingProp, movies)
            }
            return true
        }

    })
}

suspend fun MovieActivity.changeRecyclerRows(
    loadingProp: View?,
    movieSet: MutableList<Movie>?
): Unit {
    if (loadingProp == null) return
if(movieSet==null){
    adapter?.data?.clear()
    adapter?.dataFiltered?.clear()
} else{
    adapter?.data = movieSet
    adapter?.dataFiltered = movieSet
    withContext(Main) {
        if (movieSet.size == 0) {
            Toast.makeText(
                context,
                "No Movies Found",
                Toast.LENGTH_SHORT
            ).show();
        }
        loadingProp.visibility = View.GONE
        adapter?.notifyDataSetChanged()
    }}
}