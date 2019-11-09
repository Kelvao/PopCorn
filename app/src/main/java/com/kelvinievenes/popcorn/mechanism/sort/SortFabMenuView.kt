package com.kelvinievenes.popcorn.mechanism.sort

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.kelvinievenes.popcorn.R
import kotlinx.android.synthetic.main.sort_fab_menu_view_layout.view.*

class SortFabMenuView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var isOpened = false
    var clickListener: ((Option) -> Unit)? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.sort_fab_menu_view_layout, this, true)
        setupListeners()
    }

    private fun setupListeners() {
        fabMenu.setOnClickListener {
            if (isOpened) {
                close()
            } else {
                open()
            }
        }

        fabDate.setOnClickListener {
            clickListener?.invoke(Option.DATE)
            close()
        }
        fabName.setOnClickListener {
            clickListener?.invoke(Option.NAME)
            close()
        }
    }

    fun open() {
        isOpened = true
        fabMenu.setImageResource(R.drawable.ic_close)
        fabDate.animate().apply {
            translationY(-resources.getDimension(R.dimen.standard_55))
            duration = 200
        }
        fabName.animate().apply {
            translationY(-resources.getDimension(R.dimen.standard_105))
            duration = 200
        }
    }

    fun close() {
        isOpened = false
        fabMenu.setImageResource(R.drawable.ic_sort)
        fabName.animate().apply {
            translationY(0f)
            duration = 200
        }
        fabDate.animate().apply {
            translationY(0f)
            duration = 200
        }
    }

    enum class Option {
        DATE, NAME
    }

}