package  com.alligo.presentation.home


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alligo.data.repository.ProductRepository
import com.alligo.data.utils.NetworkResult
import com.alligo.model.product.Product
import com.alligo.model.product.Products
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject
constructor(
    private val productRepository: ProductRepository,

    ) : ViewModel() {


    init {

        getProducts()
    }


    val searchQuery = MutableLiveData("")
    var isSearchEnabled = false

    var page: Int = 1

    var productListScrollPosition = 0

    private val _productsState =
        MutableStateFlow<NetworkResult<out Products>>(NetworkResult.Loading)
    val productsState: MutableStateFlow<NetworkResult<out Products>> = _productsState

    val products = mutableListOf<Product>()

    fun getProducts() {
        viewModelScope.launch {
            productRepository.getProducts(page)
                .collect {
                    addData(it)
                }
        }
    }


    fun newSearch() {
        if (productsState.value is NetworkResult.Loading)
            return
        viewModelScope.launch {
            productRepository.searchProducts(page, searchQuery.value ?: "")
                .collect {
                    addData(it)
                }
        }
    }

    //
    fun nextPage() = viewModelScope.launch {
        Log.i("products", "next page is called $productListScrollPosition")

        if (productsState.value is NetworkResult.Loading)
            return@launch
        //Divide by 2 because items are in grid
        if ((productListScrollPosition + 1) >= (page * productRepository.limit) / 2) {
            page++
            Log.i("products", "next page called")

            when (isSearchEnabled) {
                false -> {
                    productRepository.getProducts(page = page).collect {
                        addData(it)
                    }
                }

                else -> {
                    productRepository.searchProducts(page = page, query = searchQuery.value ?: "")
                        .collect {
                            addData(it)
                        }
                }
            }

        }
    }


    fun refresh() = viewModelScope.launch {
        Log.i("products", "refresh is called $isSearchEnabled  ${searchQuery.value}")

        page = 1
        if (productsState.value is NetworkResult.Loading)
            return@launch

        when (isSearchEnabled) {
            false -> {
                productRepository.getProducts(page = page).collect {
                    addData(it)
                }
            }

            else -> {
                productRepository.searchProducts(page = page, query = searchQuery.value ?: "")
                    .collect {
                        addData(it)
                    }
            }
        }


    }


    //todo: check why checking null is needed (happened after adding databinding)
    private fun addData(result: NetworkResult<out Products>) {
        _productsState?.let{
            it.value = result
            if (result is NetworkResult.Success) {
                products.clear()
                products.addAll(result.data.products)
            }
        }
    }

    fun onClearSearch() {
        searchQuery.value = ""
        page = 1
        getProducts()
    }


}