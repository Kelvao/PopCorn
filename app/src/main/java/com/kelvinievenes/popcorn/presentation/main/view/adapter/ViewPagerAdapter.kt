package com.kelvinievenes.popcorn.presentation.main.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.kelvinievenes.popcorn.presentation.favorites.view.FavoritesFragment
import com.kelvinievenes.popcorn.presentation.movielist.view.MovieListFragment

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    behavior: Int
) : FragmentStatePagerAdapter(fragmentManager, behavior) {

    override fun getItem(position: Int): Fragment =
        if (position == 0) MovieListFragment()
        else FavoritesFragment()

    override fun getCount(): Int = 2
}