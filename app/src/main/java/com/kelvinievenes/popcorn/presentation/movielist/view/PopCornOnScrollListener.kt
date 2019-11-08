package com.kelvinievenes.popcorn.presentation.movielist.view

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PopCornOnScrollListener(
    private val layoutManager: LinearLayoutManager,
    private val threshold: Int
) :
    RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val totalItemCount = layoutManager.itemCount

        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

        if (totalItemCount == lastVisibleItemPosition + threshold) {
            onScrolledToThreshold()
        }
    }

    abstract fun onScrolledToThreshold()
}