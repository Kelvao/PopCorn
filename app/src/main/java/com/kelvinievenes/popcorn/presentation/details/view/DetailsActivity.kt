package com.kelvinievenes.popcorn.presentation.details.view

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.kelvinievenes.popcorn.R
import com.kelvinievenes.popcorn.domain.model.Movie
import com.kelvinievenes.popcorn.domain.model.Quality
import com.kelvinievenes.popcorn.mechanism.imageloader.GlideApp
import com.kelvinievenes.popcorn.mechanism.livedata.Status
import com.kelvinievenes.popcorn.mechanism.screenHeight
import com.kelvinievenes.popcorn.presentation.details.presenter.DetailsPresenter
import com.kelvinievenes.popcorn.presentation.details.view.adapter.GenreAdapter
import com.kelvinievenes.popcorn.presentation.details.view.adapter.RatingAdapter
import kotlinx.android.synthetic.main.activity_details.*
import org.koin.android.ext.android.inject

class DetailsActivity : AppCompatActivity() {

    private val presenter: DetailsPresenter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        setupToolbar()
        setupBackground()
        setupFavoriteFab()
        observeChanges()
        intent.getStringExtra(EXTRA_IMDB)?.let {
            presenter.getMovieDetails(it)
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setupBackground() {
        movieDetails.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            background.visibility = if (scrollY > 0) VISIBLE else GONE
            favoriteFab.apply {
                if (scrollY > 0) show()
                else hide()
            }
        }
    }

    private fun setupFavoriteFab() {
        favoriteFab.onClickListener = { isChecked ->
            if (isChecked) presenter.addFavorite()
            else presenter.removeFavorite()
        }
    }

    private fun observeChanges() {
        presenter.movieDetailsLiveData.observe(this, Observer { resource ->
            when (resource.status) {
                Status.LOADING -> loader.visibility = VISIBLE
                Status.SUCCESS -> {
                    loader.visibility = GONE
                    resource.data?.let {
                        bindDetails(it)
                    }
                }
                Status.ADD_SUCCESS -> {
                    favoriteFab.isChecked = true
                }
                Status.REMOVE_SUCCESS -> {
                    favoriteFab.isChecked = false
                }
                Status.ERROR -> {
                    loader.visibility = GONE
                    showErrorMessage(resource.message)
                }
                else -> {

                }
            }
        })
    }

    private fun bindDetails(details: Movie) {
        movieTitle.text = details.title
        year.text = getString(R.string.details_year, details.released)
        director.text = getString(R.string.details_director, details.director)
        writers.text = getString(R.string.details_writers, details.writers)
        rated.text = details.rated
        plot.text = details.plot
        bindPoster(details)
        bindGenre(details)
        bindRatings(details)
        bindFavorite(details)
    }

    private fun bindPoster(details: Movie) {
        val newSize = (screenHeight() / 100) * 80
        val layoutParams = poster.layoutParams as ConstraintLayout.LayoutParams
        layoutParams.height = newSize

        movieDetails.setPadding(0, newSize, 0, 0)

        GlideApp.with(this)
            .load(details.getPoster(Quality.HIGH))
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    isFirstResource: Boolean
                ): Boolean {
                    poster.visibility = GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            })
            .into(poster)
    }

    private fun bindGenre(details: Movie) {
        val flexboxLayoutManager = FlexboxLayoutManager(this).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
        }

        genres.apply {
            layoutManager = flexboxLayoutManager
            adapter = GenreAdapter(details.genres)
        }
    }

    private fun bindRatings(details: Movie) {
        val linearLayoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        ratings.apply {
            layoutManager = linearLayoutManager
            adapter = RatingAdapter(details.ratings)
        }
    }

    private fun bindFavorite(movie: Movie) {
        favoriteFab.isChecked = movie.isFavorite
        favoriteFab.hide()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun showErrorMessage(message: String?) {
        message?.let {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        private const val EXTRA_IMDB = "imdbId"

        @JvmStatic
        fun getStartIntent(context: Context, imdbId: String): Intent =
            Intent(context, DetailsActivity::class.java).apply {
                putExtra(EXTRA_IMDB, imdbId)
            }
    }
}
