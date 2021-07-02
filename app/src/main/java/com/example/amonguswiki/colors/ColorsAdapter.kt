package com.example.amonguswiki.colors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.amonguswiki.R
import com.squareup.picasso.Picasso


class ColorsAdapter(private val data: List<Color>, val onClick:(Color) -> Unit) :
    RecyclerView.Adapter<ColorsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_colors, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val color = data[position]
        holder.bind(color)
        holder.itemView.setOnClickListener {
            onClick(color)

        }
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: Color) {

            val tv_colors=view.findViewById<TextView>(R.id.tv_colors)
            val iv_colors=view.findViewById<ImageView>(R.id.iv_colors)

            tv_colors.text=data.name
            Picasso.get().load(data.img).into(iv_colors)

        }
    }
}