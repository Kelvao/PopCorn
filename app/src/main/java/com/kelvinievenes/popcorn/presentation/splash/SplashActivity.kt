package com.kelvinievenes.popcorn.presentation.splash

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.kelvinievenes.popcorn.R
import com.kelvinievenes.popcorn.presentation.main.view.MainActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            startActivity(MainActivity.getStartIntent(this))
            finish()
        }, 1000)
    }
}
