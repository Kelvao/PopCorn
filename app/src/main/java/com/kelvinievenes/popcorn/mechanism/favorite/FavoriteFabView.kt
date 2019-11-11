package com.kelvinievenes.popcorn.mechanism.favorite

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kelvinievenes.popcorn.R

class FavoriteFabView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FloatingActionButton(context, attrs, defStyleAttr) {

    var isChecked: Boolean = false
        set(value) {
            field = value
            changeIcon()
        }

    var onClickListener: ((Boolean) -> Unit)? = null

    init {
        setOnClickListener {
            onClickListener?.invoke(!isChecked)
        }
    }

    private fun changeIcon() {
        setImageResource(
            if (isChecked) R.drawable.ic_favorite
            else R.drawable.ic_non_favorite
        )

        // Fix: icon not showing when changed programmatically
        // Reference: https://stackoverflow.com/questions/54506295/icon-not-showing-in-floatingactionbutton-after-changed-programmatically
        hide()
        show()
    }
}