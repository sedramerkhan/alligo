package com.example.e_commercenativexml.presentation.home


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commercenativexml.data.repository.ProductRepository
import com.example.e_commercenativexml.data.utils.NetworkResult
import com.example.e_commercenativexml.model.product.Product
import com.example.e_commercenativexml.model.product.Products
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

    //  var query by mutableStateOf("")
    private var page: Int = 1
    private var tvShowListScrollPosition = 0
    var listStateTo0 = MutableLiveData(false)


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


//    private suspend fun newSearch() {
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
//    private suspend fun nextPage() {
//        if(tvShowsResponse is NetworkResult.Loading)
//            return
//        if ((tvShowListScrollPosition + 1) >= (page * PAGE_SIZE)) {
//            incrementPage()
//            Log.d(TAG, "nextPage: triggered: $page")
//            if (page > 1) {
//                when(searchDone) {
//                    false -> {
//                        repo.getPopular(page = page).collect {
//                            tvShowsResponse = it
//                            if (it is NetworkResult.Success) {
//                                tvShows.addAll(it.data)
//                            }
//                        }
//                    }
//                    else -> {
//                        repo.search(page = page, query = query).collect {
//                            tvShowsResponse = it
//                            if (it is NetworkResult.Success) {
//                                tvShows.addAll(it.data)
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }


//    private fun incrementPage() {
//        setPage(page + 1)
//    }

    fun onChangeTvShowScrollPosition(position: Int) {
        setListScrollPosition(position = position)
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

    private fun setListScrollPosition(position: Int) {
        tvShowListScrollPosition = position
    }

}