
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.amonguswiki.R
import com.example.amonguswiki.favorites.Article
import com.squareup.picasso.Picasso


class TasksAdapter(private val data: List<Article>, val onClick:(Article) -> Unit) :
    RecyclerView.Adapter<TasksAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = data[position]
        holder.bind(task)
        holder.itemView.setOnClickListener {
            onClick(task)

        }
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: Article) {

            val tv_tasks=view.findViewById<TextView>(R.id.tv_tasks)
            val iv_tasks=view.findViewById<ImageView>(R.id.iv_tasks)

            tv_tasks.text=data.name
            Picasso.get().load(data.img).into(iv_tasks)
        }
    }
}