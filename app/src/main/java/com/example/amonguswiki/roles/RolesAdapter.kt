
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.amonguswiki.R
import com.example.amonguswiki.favorites.Article
import com.squareup.picasso.Picasso

class RolesAdapter(private val data: List<Article>, val onClick:(Article) -> Unit) :
    RecyclerView.Adapter<RolesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_roles, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rol = data[position]
        holder.bind(rol)
        holder.itemView.setOnClickListener {
            onClick(rol)

        }
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: Article) {

            val iv_roles=view.findViewById<ImageView>(R.id.iv_roles)
            Picasso.get().load(data.img).into(iv_roles)
        }
    }
}