package com.kelvinievenes.popcorn.presentation.movielist.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kelvinievenes.popcorn.R
import com.kelvinievenes.popcorn.mechanism.livedata.Status
import com.kelvinievenes.popcorn.presentation.PopCornEmptyState.State
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
        observeChanges()
        presenter.getMovieList("Batman")
    }

    private fun setupRecyclerView() {
        movieListAdapter = MovieListAdapter()

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

    private fun observeChanges() {
        presenter.moviesLiveData.observe(this, Observer { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    hideEmptyState()
                    loader.visibility = VISIBLE
                }
                Status.SUCCESS -> {
                    loader.visibility = GONE
                    resource.data?.let {
                        movieListAdapter.setData(it)
                    }
                }
                Status.SUCCESS_NEXT_PAGE -> {
                    resource.data?.let {
                        movieListAdapter.addData(it)
                    }
                }
                Status.EMPTY -> showEmptyState(State.EMPTY)
                Status.EMPTY_NEXT_PAGE -> movieListAdapter.hideLoader()
                Status.ERROR -> showErrorMessage(resource.message)
                Status.ERROR_NEXT_PAGE -> showErrorMessage(resource.message)
            }
        })
    }

    private fun showEmptyState(state: State) {
        emptyState.visibility = VISIBLE
        emptyState.state = state
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
