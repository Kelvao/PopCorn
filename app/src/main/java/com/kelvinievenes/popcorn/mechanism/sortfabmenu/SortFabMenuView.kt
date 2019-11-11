package com.kelvinievenes.popcorn.mechanism.sortfabmenu

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.BounceInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import com.kelvinievenes.popcorn.R
import kotlinx.android.synthetic.main.sort_fab_menu_view_layout.view.*

class SortFabMenuView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var isOpened = false
    private var sortOption: Option? = null

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
        titleDate.translationYAnimation(-resources.getDimension(R.dimen.standard_55))
        titleDate.alphaAnimation(1f)
        fabName.translationYAnimation(-resources.getDimension(R.dimen.standard_105))
        titleName.translationYAnimation(-resources.getDimension(R.dimen.standard_105))
        titleName.alphaAnimation(1f)
    }

    private fun close() {
        isOpened = false
        fabMenu.setImageResource(R.drawable.ic_sort_menu)
        fabName.translationYAnimation(0f)
        titleName.alphaAnimation(0f)
        titleName.translationYAnimation(0f)
        fabDate.translationYAnimation(0f)
        titleDate.translationYAnimation(0f)
        titleDate.alphaAnimation(0f)
    }

    private fun View.translationYAnimation(y: Float) =
        animate().apply {
            translationY(y)
            duration = 200
            interpolator = if (y == 0f) AccelerateInterpolator() else BounceInterpolator()
        }

    private fun View.alphaAnimation(a: Float) =
        animate().apply {
            alpha(a)
            duration = 200
            withStartAction {
                if (a == 1f) {
                    visibility = VISIBLE
                    alpha = 0f
                }
            }
            withEndAction {
                if (a == 0f) {
                    visibility = View.INVISIBLE
                }
            }
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