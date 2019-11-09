package com.kelvinievenes.popcorn.presentation.details.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kelvinievenes.popcorn.R

class GenreAdapter (
    private var data: List<String>
): RecyclerView.Adapter<GenreViewHolder>() {

    init {
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        GenreViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.genre_item, parent,
                false
            )
        )

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size
}