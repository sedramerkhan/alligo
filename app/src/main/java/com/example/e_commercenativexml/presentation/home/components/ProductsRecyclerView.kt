package com.example.e_commercenativexml.presentation.home.components

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commercenativexml.R
import com.example.e_commercenativexml.databinding.ProductItemBinding
import com.example.e_commercenativexml.model.product.Product
import com.example.e_commercenativexml.model.product.Products
import com.example.e_commercenativexml.presentation.utils.ImageService


class GridAdapter() :
    RecyclerView.Adapter<GridAdapter.GridViewHolder>() {

    private val data = mutableListOf<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GridViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    fun bindData(items: List<Product>) {
        data.clear()
        data.addAll(items)
        Log.i("products","$items")
        notifyDataSetChanged()
    }

    fun addData(items: List<Product>) {
        data.addAll(items)
        notifyItemRangeChanged(data.size - items.size, items.size)
    }

    inner class GridViewHolder(private val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Product) {

            binding.productItemText.text = item.title
            ImageService.setImage(binding.productItemImage,binding.productItemImageShimmer,item.thumbnail)

        }

    }
}