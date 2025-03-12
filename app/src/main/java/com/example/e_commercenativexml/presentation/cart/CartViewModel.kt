package com.example.e_commercenativexml.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commercenativexml.data.repository.CartRepository
import com.example.e_commercenativexml.model.CartItem
import com.example.e_commercenativexml.model.product.Product
import com.example.e_commercenativexml.model.product.toCartItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {


    init {
        getItems()
    }

    private val _cartItemsState =
        MutableStateFlow<List<CartItem>>(listOf())
    val cartItemsState: MutableStateFlow<List<CartItem>> = _cartItemsState

    val cartsQuantityState: MutableStateFlow<Int> = MutableStateFlow(0)


    private fun getItems() {
        viewModelScope.launch {
            cartRepository.getAllItems()
                .collect {
                    _cartItemsState.value = it
                    cartsQuantityState.value = it.fold(0) { acc, item ->
                        acc + item.quantity

                    }
                }
        }
    }

    var counter = 1
    fun addToCart(product: Product, quantity: Int) {

        viewModelScope.launch {
            val item = product.toCartItem(counter++)
            cartRepository.insertItem(item)

        }
    }

}