
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.amonguswiki.R
import com.example.amonguswiki.favorites.Article
import com.squareup.picasso.Picasso


class LocationsAdapter(private val data: List<Article>, val onClick:(Article) -> Unit) :
    RecyclerView.Adapter<LocationsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_location, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val location = data[position]
        holder.bind(location)
        holder.itemView.setOnClickListener {
            onClick(location)

        }
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: Article) {

            val tv_locations=view.findViewById<TextView>(R.id.tv_locations)
            val iv_locations=view.findViewById<ImageView>(R.id.iv_locations)

            tv_locations.text=data.name
            Picasso.get().load(data.img).into(iv_locations)
        }
    }
}