package com.kelvinievenes.popcorn.presentation.movielist.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kelvinievenes.popcorn.R
import com.kelvinievenes.popcorn.controller.Status
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

        setupRecyclerView()
        observeChanges()
        presenter.getMovieList("Batman")
    }

    private fun setupRecyclerView() {
        movieListAdapter = MovieListAdapter()

        movieList.apply {
            adapter = movieListAdapter

            layoutManager =
                LinearLayoutManager(
                    this@MovieListActivity,
                    LinearLayoutManager.VERTICAL,
                    false
                )

            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val totalItemCount = layoutManager!!.itemCount
                    val lastVisibleItemPosition =
                        (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    if (totalItemCount == lastVisibleItemPosition + VISIBLE_THRESHOLD) {
                        presenter.getMoreMovies()
                    }
                }
            })
        }
    }

    private fun observeChanges() {
        presenter.moviesLiveData.observe(this, Observer { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    resource.data?.let {
                        movieListAdapter.setData(it)
                    }
                }
                Status.SUCCESS_NEXT_PAGE -> {
                    resource.data?.let {
                        movieListAdapter.addData(it)
                    }
                }
                Status.EMPTY -> {
                    movieListAdapter.hideLoader()
                }
                else -> {

                }
            }
        })
    }

    companion object {
        private const val VISIBLE_THRESHOLD = 5

        @JvmStatic
        fun getStartIntent(context: Context): Intent =
            Intent(context, MovieListActivity::class.java)
    }
}
