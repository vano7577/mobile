package ua.kpi.comsys.ip8404.ui.image


import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ua.kpi.comsys.ip8404.R

class ImageAdapter(private val context: Context, var dataSource: MutableList<Uri>) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>(){

    class ImageViewHolder(private val view: View) : RecyclerView.ViewHolder(view){
        val imageView: ImageView = view.findViewById(R.id.id_image_picture) as ImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val imageLayout = LayoutInflater.from(parent.context).inflate(R.layout.picture_item, parent, false)
        return ImageViewHolder(imageLayout)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {

        val param: GridLayout.LayoutParams = GridLayout.LayoutParams();
        param.rowSpec = GridLayout.spec(0, 2, null, 1F);
        param.columnSpec = GridLayout.spec(0, 2, null, 1F);

        holder.imageView.layoutParams = param
        holder.imageView.setImageURI(dataSource[position])
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }
}