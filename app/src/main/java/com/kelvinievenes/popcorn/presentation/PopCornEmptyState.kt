package com.kelvinievenes.popcorn.presentation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.kelvinievenes.popcorn.R
import kotlinx.android.synthetic.main.popcorn_empty_state.view.*

class PopCornEmptyState @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var state: State? = null
        set(value) {
            field = value
            value?.let { setupState(it) }
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.popcorn_empty_state, this, true)
    }

    private fun setupState(state: State) {
        icon.setImageResource(state.icon)
        title.text = context.getText(state.title)
        message.text = context.getText(state.message)
    }


    enum class State(
        @DrawableRes val icon: Int,
        @StringRes val title: Int,
        @StringRes val message: Int
    ) {
        INITIAL(
            R.drawable.ic_desert,
            R.string.empty_state_initial_title,
            R.string.empty_state_initial_message
        ),
        EMPTY(
            R.drawable.ic_desert,
            R.string.empty_state_empty_title,
            R.string.empty_state_empty_message
        )
    }

}