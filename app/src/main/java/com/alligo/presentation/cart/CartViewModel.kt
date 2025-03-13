package  com.alligo.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alligo.data.repository.CartRepository
import com.alligo.model.CartItem
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


    val originalPrice
        get() = cartItemsState.value.fold(0.0) { acc, item ->
            acc + item.price * item.quantity
        }

    val discount
        get() = cartItemsState.value.fold(0.0) { acc, item ->
            acc + (item.price * item.discountPercentage / 100) * item.quantity
        }

    fun increaseItemQuantity(index: Int) {
        viewModelScope.launch {
            _cartItemsState.value[index].let {
                cartRepository.insertItem(it.copy(quantity = it.quantity + 1))
            }
        }
    }

    fun decreaseItemQuantity(index: Int) {
        _cartItemsState.value[index].let {
            if (it.quantity > 1)
                viewModelScope.launch {
                    cartRepository.insertItem(it.copy(quantity = it.quantity - 1))
                }
        }
    }

    fun deleteItem(index: Int) {
        viewModelScope.launch {
            cartRepository.deleteItem(_cartItemsState.value[index].id)
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            cartRepository.deleteAll()
        }
    }

}