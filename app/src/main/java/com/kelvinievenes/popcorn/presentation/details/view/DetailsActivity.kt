package com.kelvinievenes.popcorn.presentation.details.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.kelvinievenes.popcorn.R
import com.kelvinievenes.popcorn.domain.model.Movie
import com.kelvinievenes.popcorn.mechanism.livedata.Status
import com.kelvinievenes.popcorn.presentation.details.presenter.DetailsPresenter
import org.koin.android.ext.android.inject

class DetailsActivity : AppCompatActivity() {

    private val presenter: DetailsPresenter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        observeChanges()
    }

    private fun observeChanges() {
        presenter.movieDetailsLiveData.observe(this, Observer { resource ->
            when(resource.status){
                Status.ERROR -> showErrorMessage(resource.message)
                else -> {
                    resource.data?.let {
                        setupDetails(it)
                    }
                }
            }
        })
    }

    private fun setupDetails(details: Movie) {

    }

    private fun showErrorMessage(message: String?) {
        message?.let {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
    }
}
