package  com.alligo.presentation.home


import android.util.Log
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

        getProducts(1)
    }


    var searchQuery = ""
    var isSearchEnabled = false

    var page: Int = 1

    var productListScrollPosition = 0

    private val _productsState =
        MutableStateFlow<NetworkResult<out Products>>(NetworkResult.Loading)
    val productsState: MutableStateFlow<NetworkResult<out Products>> = _productsState

    val products = mutableListOf<Product>()

    fun getProducts(page: Int) {
        viewModelScope.launch {
            productRepository.getProducts(page)
                .collect {
                    _productsState.value = it
                    if (it is NetworkResult.Success) {
                        products.clear()
                        products.addAll(it.data.products)

                    }
                }
        }
    }


    //     suspend fun newSearch() {
//        cleanData()
//        val results: MutableList<TvShow> = mutableListOf()
//        for (p in 1..page) {
//            repo.search(page = p, query = query).collect {
//                tvShowsResponse = it
//                if (it is NetworkResult.Success) {
//                    results.addAll(it.data)
//                }
//                if (p == page) { // done
//                    tvShows.addAll(results)
//                    searchDone = true
//                    setListState()
//                }
//            }
//            Log.d("newSearch", results.size.toString())
//        }
//    }
//
    fun nextPage() = viewModelScope.launch {
        Log.i("products","next page is called $productListScrollPosition")

        if (productsState.value is NetworkResult.Loading)
            return@launch
        //Divide by 2 because items are in grid
        if ((productListScrollPosition +1) >= (page * productRepository.limit)/2) {
            page++
            Log.i("products","next page called")

            when (isSearchEnabled) {
                false -> {
                    productRepository.getProducts(page = page).collect {
                        addData(it)
                    }
                }

                else -> {
                    productRepository.searchProducts(page = page, query = searchQuery).collect {
                        addData(it)
                    }
                }
            }

        }
    }


    fun refresh() = viewModelScope.launch {
        Log.i("products","refresh is called")

        page=1
        if (productsState.value is NetworkResult.Loading)
            return@launch

            when (isSearchEnabled) {
                false -> {
                    productRepository.getProducts(page = page).collect {
                        addData(it)
                    }
                }

                else -> {
                    productRepository.searchProducts(page = page, query = searchQuery).collect {
                        addData(it)
                    }
                }
            }


    }


    private fun addData(result: NetworkResult<out Products>) {
        _productsState.value = result
        if (result is NetworkResult.Success) {
            products.clear()
            products.addAll(result.data.products)
        }
    }

    /**
     * Called when a new search is executed or Restoring data after closing searh bar.
     */
//    private fun cleanData() {
//        tvShows.clear()
//        onChangeTvShowScrollPosition(0)
//    }
//
//    fun onQueryChanged(query: String) {
//        setQuery(query)
//    }



}