package com.alligo.presentation.productDetails.components

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alligo.databinding.ItemProductReviewBinding
import com.alligo.model.product.Review
import com.alligo.presentation.utils.extentions.formatDate

class ReviewPagerAdapter(private val reviews: List<Review>) : RecyclerView.Adapter<ReviewPagerAdapter.ReviewViewHolder>() {

    inner class ReviewViewHolder(val binding: ItemProductReviewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding = ItemProductReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]

        // Bind the review data to the views
        holder.binding.reviewName.text = review.reviewerName
        holder.binding.reviewRating.text =  review.rating.toString()
        holder.binding.reviewComment.text = review.comment
        holder.binding.reviewDate.text = review.date.formatDate()
    }

    override fun getItemCount() = reviews.size
}

