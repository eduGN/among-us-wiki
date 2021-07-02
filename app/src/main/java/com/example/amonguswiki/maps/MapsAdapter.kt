package com.example.amonguswiki.maps

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.amonguswiki.R
import com.squareup.picasso.Picasso

class MapsAdapter(private val data:ArrayList<Map>, val onClick:(Map) -> Unit) :
    RecyclerView.Adapter<MapsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapsAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_maps, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val map = data[position]
        holder.bind(map)
        holder.itemView.setOnClickListener {
            onClick(map)

        }
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: Map) {

            val iv_iconmaps=view.findViewById<ImageView>(R.id.iv_iconmaps)
            val iv_maps=view.findViewById<ImageView>(R.id.iv_maps)

            Picasso.get().load(data.icon).into(iv_iconmaps)
            Picasso.get().load(data.img).into(iv_maps)

        }
    }
}