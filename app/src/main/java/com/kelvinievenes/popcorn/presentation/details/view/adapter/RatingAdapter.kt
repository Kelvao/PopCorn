package com.kelvinievenes.popcorn.presentation.details.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kelvinievenes.popcorn.R
import com.kelvinievenes.popcorn.domain.model.Rating

class RatingAdapter (
    private var data: List<Rating>
): RecyclerView.Adapter<RatingViewHolder>() {

    init {
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RatingViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rating_item, parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RatingViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size
}