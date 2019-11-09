package com.kelvinievenes.popcorn.presentation.movielist.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.loader_item.view.*

class LoaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun showLoader(isShow: Boolean) {
        itemView.loader.visibility = if (isShow) View.VISIBLE else View.GONE
    }
}