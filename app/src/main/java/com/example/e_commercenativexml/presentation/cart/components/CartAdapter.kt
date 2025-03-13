package com.example.e_commercenativexml.presentation.cart.components

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commercenativexml.databinding.ItemCartBinding
import com.example.e_commercenativexml.model.CartItem
import com.example.e_commercenativexml.presentation.utils.ImageService
import com.example.e_commercenativexml.presentation.utils.extentions.formatPrice

class CartAdapter(
    private val onItemIncreased: (Int) -> Unit,
    private val onItemDecreased: (Int) -> Unit,
) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private var items: MutableList<CartItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(position,items[position])
    }

    override fun getItemCount(): Int = items.size


    fun bindData(cartItems: List<CartItem>) {
        items = cartItems.toMutableList()
        notifyDataSetChanged()
    }

    fun removeItem(index: Int) {
        items.removeAt(index)
        notifyItemRemoved(index)
    }

    fun addItem(index: Int, item: CartItem) {
        items.add(index, item)
        notifyItemInserted(index)
    }

    fun updateItem(index: Int, item: CartItem) {
        items[index] = item
        notifyItemInserted(index)
    }


    inner class CartViewHolder(private val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(index: Int,item: CartItem) {
            binding.cartItemTitle.text = item.title

            binding.cartItemPrice.text = (item.price - item.price * item.discountPercentage/100).formatPrice()

            binding.cartItemOldPrice.text = item.price.formatPrice()
            binding.cartItemOldPrice.paintFlags =
                binding.cartItemOldPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            binding.cartQuantityController.counterQuantity.text= item.quantity.toString()
            binding.cartQuantityController.counterBtnIncrease.setOnClickListener{
                onItemIncreased(index)
            }

            binding.cartQuantityController.counterBtnDecrease.setOnClickListener{
                onItemDecreased(index)
            }

            ImageService.setImage(
                binding.cartItemImage.image,
                binding.cartItemImage.imageShimmer,
                item.thumbnail
            )

        }
    }
}
