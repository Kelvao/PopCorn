package com.kelvinievenes.popcorn.presentation.movielist.view.adapter

import android.graphics.drawable.Drawable
import android.view.View
import android.view.View.GONE
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.kelvinievenes.popcorn.R
import com.kelvinievenes.popcorn.domain.model.Movie
import com.kelvinievenes.popcorn.mechanism.imageloader.GlideApp
import kotlinx.android.synthetic.main.movie_item.view.*

class MovieViewHolder(
    itemView: View,
    private var onMovieItemClick: ((Movie) -> Unit)? = null
) : RecyclerView.ViewHolder(itemView) {

    fun bind(movie: Movie) {
        itemView.setOnClickListener { onMovieItemClick?.invoke(movie) }

        itemView.apply {
            title.text = movie.title
            year.text = "(${movie.year})"
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
                        loader.visibility = GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        poster.scaleType = ImageView.ScaleType.CENTER_CROP
                        return false
                    }

                })
                .into(poster)
        }
    }

}