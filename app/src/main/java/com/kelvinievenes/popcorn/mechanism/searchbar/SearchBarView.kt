package com.kelvinievenes.popcorn.mechanism.searchbar

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.kelvinievenes.popcorn.R
import kotlinx.android.synthetic.main.search_bar_view_layout.view.*
import java.util.*


class SearchBarView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.search_bar_view_layout, this, true)
    }

    fun addTextChangedListener(textChangedListener: (query: String) -> Unit) {
        searchInput.addTextChangedListener(object : TextWatcher {
            private var timer = Timer()

            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrBlank()) {
                    timer.cancel()
                    timer = Timer()
                    timer.schedule(object : TimerTask() {
                        override fun run() {
                            textChangedListener(s.toString())
                        }
                    }, DELAY)
                }
            }
        })
    }

    companion object {
        private const val DELAY: Long = 1000
    }

}