package com.alligo.presentation.productDetails.components

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alligo.databinding.ItemProductTagBinding

class TagsAdapter(
    private val tags: List<String>
) : RecyclerView.Adapter<TagsAdapter.TagViewHolder>() {

    inner class TagViewHolder(private val binding: ItemProductTagBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tag: String) {
            binding.productTag.text = tag
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductTagBinding.inflate(inflater, parent, false)
        return TagViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.bind(tags[position])
    }

    override fun getItemCount(): Int = tags.size
}
