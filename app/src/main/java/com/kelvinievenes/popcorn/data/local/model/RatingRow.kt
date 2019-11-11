package com.kelvinievenes.popcorn.data.local.model

import com.kelvinievenes.popcorn.domain.model.Rating

data class RatingRow(
    var source: String,
    var value: String
) {

    constructor(rating: Rating): this(
        rating.source, rating.value
    )

}