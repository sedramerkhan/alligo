package  com.alligo.presentation.addToCart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alligo.data.repository.CartRepository
import com.alligo.model.product.Product
import com.alligo.model.product.toCartItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddToCartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {


    private var _product: Product? = null

    val itemQuantityState: MutableStateFlow<Int> = MutableStateFlow(1)

    init {
        Log.i("product", "init")

    }

    fun setProduct(product: Product) {
        this._product = product
        getCartItem()
    }

    fun getCartItem() {
        Log.i("products", "getCartItem")

        viewModelScope.launch {
            cartRepository.getItemById(_product!!.id.toLong()).collect {
                itemQuantityState.value = it?.quantity ?: 1
            }
        }
    }

    fun addToCart() {
        viewModelScope.launch {
            Log.i("products", "add")

            val item = _product!!.toCartItem(itemQuantityState.value)
            cartRepository.insertItem(item)
        }
    }

    fun increaseQuantity() {
        Log.i("products", "increase")
        if (_product!!.stock > itemQuantityState.value)
            itemQuantityState.value++
    }

    fun decreaseQuantity() {
        Log.i("products", "decrease")
        if (itemQuantityState.value > 1)
            itemQuantityState.value--
    }


}