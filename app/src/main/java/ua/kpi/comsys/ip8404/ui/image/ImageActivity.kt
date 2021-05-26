package ua.kpi.comsys.ip8404.ui.image

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.arasthel.spannedgridlayoutmanager.SpanSize
import com.arasthel.spannedgridlayoutmanager.SpannedGridLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ua.kpi.comsys.ip8404.R
import ua.kpi.comsys.ip8404.ui.movie.fetchImageInfoFromWeb
import ua.kpi.comsys.ip8404.ui.movie.getBitmapFromURL

class ImageActivity : Fragment() {

    private var imageAdapter: ImageAdapter? = null
    private val REQUEST_IMAGE_CODE = 1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_image, container, false)

        val recyclerview = view.findViewById<RecyclerView>(R.id.id_recycler_pictures)

        val spannedGridLayoutManager = SpannedGridLayoutManager(
            orientation = SpannedGridLayoutManager.Orientation.VERTICAL,
            spans = 4
        )

        spannedGridLayoutManager.spanSizeLookup =
            SpannedGridLayoutManager.SpanSizeLookup { position ->
                when (position % 9) {
                    1 -> SpanSize(2, 2)
                    5 -> SpanSize(2, 2)
                    else ->
                        SpanSize(1, 1)
                }
            }

        val dataSource = mutableListOf<Bitmap>()
        spannedGridLayoutManager.itemOrderIsStable = true
        recyclerview.layoutManager = spannedGridLayoutManager
        imageAdapter = context?.let { ImageAdapter(it, dataSource) }
        recyclerview.adapter = imageAdapter

        view.findViewById<Button>(R.id.id_add_picture_button).setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 2)
        }
        val ctx = context
        CoroutineScope(Dispatchers.IO).launch {
            if (context == null) error("context == null")
            imageAdapter?.let {
                if (ctx != null) {
                    fillImages(it, view, ctx)
                }
            }
        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val uri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, uri)
            imageAdapter!!.dataSource.add(bitmap)
            imageAdapter!!.notifyDataSetChanged()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {}
}

suspend fun fillImages(adapter: ImageAdapter, view: View, ctx: Context): Boolean {
    val imageInfo = fetchImageInfoFromWeb("fun+party", 30)?.hits ?: return false
    for ((index, image) in imageInfo.withIndex()) {
        val bitmap =
            imageInfo[index].previewURL.let { getBitmapFromURL(it) } ?: BitmapFactory.decodeStream(
                ctx.assets.open("movie.png")
            )
        adapter.dataSource.add(bitmap)
    }

    withContext(Dispatchers.Main) {
        val loadingProp: View? = view.findViewById(R.id.loadingProp)
        if (loadingProp != null) {
            loadingProp.visibility = View.GONE
        }
        adapter.notifyDataSetChanged()
    }
    return true
}
