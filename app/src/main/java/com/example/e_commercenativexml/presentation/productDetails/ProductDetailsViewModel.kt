package com.example.e_commercenativexml.presentation.productDetails

import androidx.lifecycle.ViewModel
import com.example.e_commercenativexml.data.repository.CartRepository
import com.example.e_commercenativexml.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
) : ViewModel() {


}