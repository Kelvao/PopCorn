package com.kelvinievenes.popcorn.presentation.main.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kelvinievenes.popcorn.R
import com.kelvinievenes.popcorn.mechanism.hideKeyboard
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
            hideKeyboard()
            when (it.itemId) {
                R.id.actionMovieList -> nonSwipeableViewPager.setCurrentItem(0, true)
                R.id.actionFavorites -> nonSwipeableViewPager.setCurrentItem(1, true)
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
