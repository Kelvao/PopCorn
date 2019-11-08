package com.kelvinievenes.popcorn.presentation.movielist.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kelvinievenes.popcorn.R
import com.kelvinievenes.popcorn.domain.model.Movie

class MovieListAdapter(
    private var data: MutableList<Movie> = mutableListOf()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int) =
        if (position < data.size && data.size > 0) ITEM_MOVIE else ITEM_LOADER

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        if (viewType == ITEM_MOVIE) {
            MovieViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.movie_item, parent,
                    false
                )
            )
        } else {
            LoaderViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.loader_item, parent,
                    false
                )
            )
        }

    override fun getItemCount() = data.size + 1

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (holder is LoaderViewHolder && payloads.isNotEmpty() && payloads[0] is Boolean) {
            holder.showLoader(payloads[0] as Boolean)
        }
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MovieViewHolder) {
            holder.bind(data[position])
        }
    }

    fun setData(newData: MutableList<Movie>) {
        data = newData
        notifyDataSetChanged()
    }

    fun addData(newData: MutableList<Movie>) {
        val lastSize = data.size
        data.addAll(newData)
        notifyItemRangeChanged(lastSize, data.size)
    }

    fun hideLoader() {
        notifyItemChanged(data.size, false)
    }

    companion object {
        const val ITEM_MOVIE = 0
        const val ITEM_LOADER = 1
    }
}