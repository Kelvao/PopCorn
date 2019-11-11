package com.kelvinievenes.popcorn.mechanism.emptystate

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.kelvinievenes.popcorn.R
import kotlinx.android.synthetic.main.empty_state_view_layout.view.*

class EmptyStateView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.empty_state_view_layout, this, true)
    }

    private fun setupState(state: State) {
        icon.setImageResource(state.icon)
        title.text = context.getText(state.title)
        message.text = context.getText(state.message)
    }

    fun show(state: State) {
        visibility = VISIBLE
        setupState(state)
    }

    fun hide() {
        visibility = GONE
    }

    enum class State(
        @DrawableRes val icon: Int,
        @StringRes val title: Int,
        @StringRes val message: Int
    ) {
        INITIAL_MOVIE_LIST(
            R.drawable.ic_desert,
            R.string.empty_state_initial_title,
            R.string.empty_state_initial_message
        ),
        EMPTY_MOVIE_LIST(
            R.drawable.ic_desert,
            R.string.empty_state_empty_movie_list_title,
            R.string.empty_state_empty_movie_list_message
        ),
        EMPTY_FAVORITES(
            R.drawable.ic_desert,
            R.string.empty_state_empty_favorite_title,
            R.string.empty_state_empty_favorite_message
        ),
        EMPTY_FAVORITES_SEARCH(
            R.drawable.ic_desert,
            R.string.empty_state_empty_favorite_search_title,
            R.string.empty_state_empty_favorite_search_message
        )
    }

}