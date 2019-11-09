package com.kelvinievenes.popcorn.presentation.details.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.genre_item.view.*

class GenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(genre: String) { itemView.genre.text = genre }
}