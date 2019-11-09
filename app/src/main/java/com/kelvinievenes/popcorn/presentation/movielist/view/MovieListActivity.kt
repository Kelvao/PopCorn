package com.kelvinievenes.popcorn.presentation.movielist.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kelvinievenes.popcorn.R
import com.kelvinievenes.popcorn.mechanism.livedata.Status
import com.kelvinievenes.popcorn.mechanism.emptystate.EmptyStateView.State
import com.kelvinievenes.popcorn.presentation.details.view.DetailsActivity
import com.kelvinievenes.popcorn.presentation.movielist.presenter.MovieListPresenter
import com.kelvinievenes.popcorn.presentation.movielist.view.adapter.MovieListAdapter
import kotlinx.android.synthetic.main.activity_movie_list.*
import org.koin.android.ext.android.inject

class MovieListActivity : AppCompatActivity() {

    private val presenter: MovieListPresenter by inject()
    private lateinit var movieListAdapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        showEmptyState(State.INITIAL)
        setupRecyclerView()
        setupFabMenu()
        setupSearchBar()
        observeChanges()
    }

    private fun setupRecyclerView() {
        movieListAdapter = MovieListAdapter {
            startActivity(DetailsActivity.getStartIntent(this, it.imdbId))
        }

        val linearLayoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        movieList.apply {
            adapter = movieListAdapter
            layoutManager = linearLayoutManager
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            addOnScrollListener(object : PopCornOnScrollListener(linearLayoutManager, 5) {
                override fun onScrolledToThreshold() {
                    presenter.getMoreMovies()
                }
            })
        }
    }

    private fun setupFabMenu() {
        fabMenuSort.clickListener = {
            movieListAdapter.order = it
        }
    }

    private fun setupSearchBar() {
        searchBar.addTextChangedListener { search ->
            presenter.getMovieList(search)
        }
    }

    private fun observeChanges() {
        presenter.moviesLiveData.observe(this, Observer { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    hideEmptyState()
                    loader.visibility = VISIBLE
                    movieList.visibility = GONE
                    fabMenuSort.visibility = GONE
                }
                Status.LOADING_NEXT_PAGE -> {
                    movieListAdapter.showLoader()
                }
                Status.SUCCESS -> {
                    movieList.scrollToPosition(0)
                    resource.data?.let {
                        movieListAdapter.setData(it)
                    }
                    loader.visibility = GONE
                    movieList.visibility = VISIBLE
                    fabMenuSort.visibility = VISIBLE
                }
                Status.SUCCESS_NEXT_PAGE -> {
                    resource.data?.let {
                        movieListAdapter.addData(it)
                    }
                    movieListAdapter.hideLoader()
                }
                Status.EMPTY -> {
                    showEmptyState(State.EMPTY)
                    loader.visibility = GONE
                    fabMenuSort.visibility = GONE
                }
                Status.EMPTY_NEXT_PAGE -> {
                    movieListAdapter.hideLoader()
                }
                Status.ERROR -> {
                    showErrorMessage(resource.message)
                    movieListAdapter.hideLoader()
                    loader.visibility = GONE
                    fabMenuSort.visibility = GONE
                }
                Status.ERROR_NEXT_PAGE -> {
                    showErrorMessage(resource.message)
                    movieListAdapter.hideLoader()
                }
            }
        })
    }

    private fun showEmptyState(state: State) {
        runOnUiThread {
            emptyState.visibility = VISIBLE
            emptyState.state = state
        }
    }

    private fun hideEmptyState() {
        emptyState.visibility = GONE
    }

    private fun showErrorMessage(message: String?) {
        message?.let {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        @JvmStatic
        fun getStartIntent(context: Context): Intent =
            Intent(context, MovieListActivity::class.java)
    }
}
