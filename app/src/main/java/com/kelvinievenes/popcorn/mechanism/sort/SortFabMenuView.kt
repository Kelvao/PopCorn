package com.kelvinievenes.popcorn.mechanism.sort

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.kelvinievenes.popcorn.R
import kotlinx.android.synthetic.main.sort_fab_menu_view_layout.view.*

class SortFabMenuView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var isOpened = false
    var sortOption: Option? = null

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
            sortOption = Option.DATE
            clickListener?.invoke(Option.DATE)
            close()
        }
        fabName.setOnClickListener {
            sortOption = Option.NAME
            clickListener?.invoke(Option.NAME)
            close()
        }
    }

    private fun open() {
        isOpened = true
        fabMenu.setImageResource(R.drawable.ic_close)
        fabDate.translationYAnimation(-resources.getDimension(R.dimen.standard_55))
        fabName.translationYAnimation(-resources.getDimension(R.dimen.standard_105))
    }

    private fun close() {
        isOpened = false
        fabMenu.setImageResource(R.drawable.ic_sort_menu)
        fabName.translationYAnimation(0f)
        fabDate.translationYAnimation(0f)
    }

    private fun View.translationYAnimation(y: Float) =
        animate().apply {
            translationY(y)
            duration = 200
        }

    fun show() {
        visibility = VISIBLE
    }

    fun hide() {
        visibility = GONE
    }

    enum class Option {
        DATE, NAME
    }

}