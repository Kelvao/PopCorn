package com.kelvinievenes.popcorn.presentation.favorites.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kelvinievenes.popcorn.R
import com.kelvinievenes.popcorn.mechanism.emptystate.EmptyStateView
import com.kelvinievenes.popcorn.mechanism.livedata.Status
import com.kelvinievenes.popcorn.presentation.base.adapter.adapter.MovieListAdapter
import com.kelvinievenes.popcorn.presentation.details.view.DetailsActivity
import com.kelvinievenes.popcorn.presentation.favorites.presenter.FavoritesPresenter
import kotlinx.android.synthetic.main.base_fragment_list.*
import org.koin.android.ext.android.inject

class FavoritesFragment : Fragment() {

    private val presenter: FavoritesPresenter by inject()
    private lateinit var favoritesAdapter: MovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.base_fragment_list, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        emptyState.hide()
        setupRecyclerView()
        setupFabMenu()
        setupSearchBar()
        observeChanges()
        presenter.getAllFavorites()
    }

    private fun setupRecyclerView() {
        favoritesAdapter = MovieListAdapter(false) {
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
            adapter = favoritesAdapter
            layoutManager = linearLayoutManager
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }

    private fun setupFabMenu() {
        fabMenuSort.clickListener = {
            favoritesAdapter.sort = it
        }
    }

    private fun setupSearchBar() {
        searchBar.addTextChangedListener { search ->
            presenter.getFavorites(search)
        }
    }

    private fun observeChanges() {
        presenter.favoritesLiveData.observe(this, Observer { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    emptyState.hide()
                    fabMenuSort.hide()
                    loader.visibility = VISIBLE
                    movieList.visibility = GONE
                }
                Status.SUCCESS -> {
                    resource.data?.let {
                        favoritesAdapter.data = it
                        fabMenuSort.show()
                        emptyState.hide()
                        movieList.visibility = VISIBLE
                    } ?: emptyState.show(EmptyStateView.State.EMPTY_FAVORITES)
                    loader.visibility = GONE
                }
                Status.EMPTY -> {
                    emptyState.show(EmptyStateView.State.EMPTY_FAVORITES)
                    fabMenuSort.hide()
                    loader.visibility = GONE
                    movieList.visibility = GONE
                }
                Status.EMPTY_SEARCH -> {
                    emptyState.show(EmptyStateView.State.EMPTY_FAVORITES_SEARCH)
                    fabMenuSort.hide()
                    loader.visibility = GONE
                    movieList.visibility = GONE
                }
                Status.ERROR -> {
                    showErrorMessage(resource.message)
                }
                else -> {

                }
            }
        })
    }

    private fun showErrorMessage(message: String?) {
        message?.let {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.getFavorites()
    }
}