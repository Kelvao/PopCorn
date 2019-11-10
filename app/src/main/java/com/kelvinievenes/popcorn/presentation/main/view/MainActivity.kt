package com.kelvinievenes.popcorn.presentation.main.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEachIndexed
import com.kelvinievenes.popcorn.R
import com.kelvinievenes.popcorn.presentation.main.view.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupBottomNavigationView()
        setupViewPager()
    }

    private fun setupViewPager() {
        nonSwipeableViewPager.adapter = ViewPagerAdapter(supportFragmentManager, 0)
    }

    private fun setupBottomNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.actionMovieList -> nonSwipeableViewPager.currentItem = 0
                R.id.actionFavorites -> nonSwipeableViewPager.currentItem = 1
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    companion object {
        @JvmStatic
        fun getStartIntent(context: Context): Intent =
            Intent(context, MainActivity::class.java)
    }
}
