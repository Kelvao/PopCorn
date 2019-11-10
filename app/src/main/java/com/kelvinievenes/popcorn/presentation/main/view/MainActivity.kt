package com.kelvinievenes.popcorn.presentation.main.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kelvinievenes.popcorn.R
import com.kelvinievenes.popcorn.presentation.movielist.view.MovieListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var movieListFragment: MovieListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupBottomNavigationView()
        setupFragments()
    }

    private fun setupFragments() {
        movieListFragment = MovieListFragment()
        bottomNavigationView.selectedItemId = R.id.actionMovieList
    }

    private fun setupBottomNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.actionMovieList -> handleFragments(movieListFragment)
                R.id.actionFavorites -> {

                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun handleFragments(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, fragment).commit()
        }
    }

    companion object {
        @JvmStatic
        fun getStartIntent(context: Context): Intent =
            Intent(context, MainActivity::class.java)
    }
}
