package com.kelvinievenes.popcorn.presentation.movielist.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kelvinievenes.popcorn.R
import com.kelvinievenes.popcorn.mechanism.emptystate.EmptyStateView
import com.kelvinievenes.popcorn.mechanism.livedata.Status
import com.kelvinievenes.popcorn.presentation.details.view.DetailsActivity
import com.kelvinievenes.popcorn.presentation.movielist.presenter.MovieListPresenter
import com.kelvinievenes.popcorn.presentation.movielist.view.adapter.MovieListAdapter
import kotlinx.android.synthetic.main.fragment_movie_list.*
import org.koin.android.ext.android.inject

class MovieListFragment : Fragment() {

    private val presenter: MovieListPresenter by inject()
    private lateinit var movieListAdapter: MovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_movie_list, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        showEmptyState(EmptyStateView.State.INITIAL)
        setupRecyclerView()
        setupFabMenu()
        setupSearchBar()
        observeChanges()
    }

    private fun setupRecyclerView() {
        movieListAdapter = MovieListAdapter {
            activity?.let { context ->
                startActivity(DetailsActivity.getStartIntent(context, it.imdbId))
            }
        }

        val linearLayoutManager = LinearLayoutManager(
            activity,
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
                    loader.visibility = View.VISIBLE
                    movieList.visibility = View.GONE
                    fabMenuSort.visibility = View.GONE
                }
                Status.LOADING_NEXT_PAGE -> {
                    movieListAdapter.showLoader()
                }
                Status.SUCCESS -> {
                    movieList.scrollToPosition(0)
                    resource.data?.let {
                        movieListAdapter.setData(it)
                    }
                    loader.visibility = View.GONE
                    movieList.visibility = View.VISIBLE
                    fabMenuSort.visibility = View.VISIBLE
                }
                Status.SUCCESS_NEXT_PAGE -> {
                    resource.data?.let {
                        movieListAdapter.addData(it)
                    }
                    movieListAdapter.hideLoader()
                }
                Status.EMPTY -> {
                    showEmptyState(EmptyStateView.State.EMPTY)
                    loader.visibility = View.GONE
                    fabMenuSort.visibility = View.GONE
                }
                Status.EMPTY_NEXT_PAGE -> {
                    movieListAdapter.hideLoader()
                }
                Status.ERROR -> {
                    showErrorMessage(resource.message)
                    movieListAdapter.hideLoader()
                    loader.visibility = View.GONE
                    fabMenuSort.visibility = View.GONE
                }
                Status.ERROR_NEXT_PAGE -> {
                    showErrorMessage(resource.message)
                    movieListAdapter.hideLoader()
                }
                else -> {

                }
            }
        })
    }

    private fun showEmptyState(state: EmptyStateView.State) {
        emptyState.visibility = View.VISIBLE
        emptyState.state = state
    }

    private fun hideEmptyState() {
        emptyState.visibility = View.GONE
    }

    private fun showErrorMessage(message: String?) {
        message?.let {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        }
    }

}
