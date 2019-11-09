package com.kelvinievenes.popcorn.presentation

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.kelvinievenes.popcorn.R
import com.kelvinievenes.popcorn.presentation.movielist.view.MovieListActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler().postDelayed({
            startActivity(MovieListActivity.getStartIntent(this))
            finish()
        }, 1000)
    }
}
