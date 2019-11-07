package com.kelvinievenes.popcorn.presentation.movielist.view.adapter

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.kelvinievenes.popcorn.R
import com.kelvinievenes.popcorn.controller.GlideApp
import com.kelvinievenes.popcorn.domain.model.Movie
import kotlinx.android.synthetic.main.movie_item.view.*

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(movie: Movie) {
        itemView.apply {
            title.text = movie.title.plus(" (${movie.year})")
            GlideApp.with(this)
                .load(movie.poster)
                .error(R.drawable.ic_poster)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        poster.scaleType = ImageView.ScaleType.FIT_CENTER
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean = false

                })
                .into(poster)
        }
    }

}