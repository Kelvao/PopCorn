package com.kelvinievenes.popcorn.presentation.details.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kelvinievenes.popcorn.domain.model.Rating
import kotlinx.android.synthetic.main.rating_item.view.*

class RatingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(rating: Rating) {
        itemView.apply {
            source.text = rating.source.plus(":")
            value.text = rating.value
        }
    }
}