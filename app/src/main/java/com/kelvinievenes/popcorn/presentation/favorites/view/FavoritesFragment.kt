package com.kelvinievenes.popcorn.presentation.favorites.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kelvinievenes.popcorn.R
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
    }

}