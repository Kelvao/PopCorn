package com.kelvinievenes.popcorn.presentation.favorites.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.kelvinievenes.popcorn.R
import com.kelvinievenes.popcorn.mechanism.livedata.Status
import com.kelvinievenes.popcorn.presentation.favorites.presenter.FavoritesPresenter
import org.koin.android.ext.android.inject

class FavoritesFragment : Fragment() {

    private val presenter: FavoritesPresenter by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_favorites, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        observeChanges()
        presenter.getFavorites()
    }

    private fun observeChanges() {
        presenter.favoritesLiveData.observe(this, Observer {
            when (it.status) {
                Status.LOADING -> {

                }
                Status.SUCCESS -> {

                }
                Status.REMOVE_SUCCESS -> {

                }
                Status.ERROR -> {

                }
                else -> {

                }
            }
        })
    }

}