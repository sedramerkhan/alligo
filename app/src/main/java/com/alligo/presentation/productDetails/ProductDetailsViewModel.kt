package  com.alligo.presentation.productDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alligo.data.repository.CartRepository
import com.alligo.data.repository.ProductRepository
import com.alligo.data.utils.NetworkResult
import com.alligo.model.product.Product
import com.alligo.model.product.Products
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val productRepository: ProductRepository,
) : ViewModel() {


    private val _productState =
        MutableStateFlow<NetworkResult<out Product>>(NetworkResult.Loading)
    val productsState: MutableStateFlow<NetworkResult<out Product>> = _productState


    fun getProduct(id: Int) {
        viewModelScope.launch {
            productRepository.getProduct(id)
                .collect {
                    _productState.value = it

                }
        }
    }

}