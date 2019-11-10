package com.kelvinievenes.popcorn.presentation.main.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kelvinievenes.popcorn.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupBottomNavigationView()
    }

    private fun setupBottomNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.actionMovieList -> {

                }
                R.id.actionFavorites -> {

                }
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
