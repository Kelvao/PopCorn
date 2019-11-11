package com.kelvinievenes.popcorn.presentation.base.adapter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kelvinievenes.popcorn.R
import com.kelvinievenes.popcorn.domain.model.Movie
import com.kelvinievenes.popcorn.mechanism.sortfabmenu.SortFabMenuView

class MovieListAdapter(
    private var hasLoader: Boolean = false,
    private var onMovieItemClick: ((Movie) -> Unit)? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data: MutableList<Movie> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var sort: SortFabMenuView.Option? = null
        set(value) {
            field = value
            sortDataIfNecessary()
            notifyDataSetChanged()
        }

    override fun getItemViewType(position: Int) =
        if (position < data.size && data.size > 0) ITEM_MOVIE else ITEM_LOADER

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        if (viewType == ITEM_MOVIE) {
            MovieViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.movie_item, parent,
                    false
                ), onMovieItemClick
            )
        } else {
            LoaderViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.loader_item, parent,
                    false
                )
            )
        }

    override fun getItemCount() = data.size + if (hasLoader) 1 else 0

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (holder is LoaderViewHolder
            && payloads.isNotEmpty()
            && payloads[0] is Boolean
        ) {
            holder.showLoader(payloads[0] as Boolean)
        }
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MovieViewHolder) {
            holder.bind(data[position])
        }
    }

    fun addAllData(newData: MutableList<Movie>) {
        val lastSize = data.size
        data.addAll(newData)
        sortDataIfNecessary()
        notifyItemRangeChanged(lastSize, data.size)
    }

    private fun sortDataIfNecessary() {
        sort?.let { option ->
            when (option) {
                SortFabMenuView.Option.NAME -> data.sortBy { it.title }
                SortFabMenuView.Option.DATE -> data.sortBy { it.year }
            }
        }
    }

    fun showLoader() {
        notifyItemChanged(itemCount, true)
    }

    fun hideLoader() {
        notifyItemChanged(itemCount, false)
    }

    companion object {
        const val ITEM_MOVIE = 0
        const val ITEM_LOADER = 1
    }

    enum class Sort {
        NAME, DATE
    }
}