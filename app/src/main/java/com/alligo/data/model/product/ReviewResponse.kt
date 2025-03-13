package  com.alligo.data.model.product

import com.alligo.model.product.Review

data class ReviewResponse(
    val comment: String,
    val date: String,
    val rating: Int,
    val reviewerEmail: String,
    val reviewerName: String
)

val ReviewResponse.asDomainModel: Review
    get() = Review(
        comment = comment,
        date = date,
        rating = rating,
        reviewerEmail = reviewerEmail,
        reviewerName = reviewerName
    )
