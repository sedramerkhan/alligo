package  com.alligo.presentation.productDetails

import androidx.lifecycle.ViewModel
import com.alligo.data.repository.CartRepository
import com.alligo.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
) : ViewModel() {


}