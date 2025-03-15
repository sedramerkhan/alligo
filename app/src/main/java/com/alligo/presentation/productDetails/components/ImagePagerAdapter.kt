package com.alligo.presentation.productDetails.components

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alligo.databinding.CustomImageViewBinding
import com.alligo.presentation.utils.ImageService

class ImagePagerAdapter(private val images: List<String>) :
    RecyclerView.Adapter<ImagePagerAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(private val binding: CustomImageViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imageUrl: String) {
           ImageService.setImage(binding.image,binding.imageShimmer,imageUrl)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = CustomImageViewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int = images.size
}
