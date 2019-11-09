package com.kelvinievenes.popcorn.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kelvinievenes.popcorn.R
import com.kelvinievenes.popcorn.presentation.movielist.view.MovieListActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(MovieListActivity.getStartIntent(this))
        finish()
    }
}
