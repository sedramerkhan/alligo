package  com.alligo.data.repository

import android.util.Log
import com.alligo.data.model.product.asDomainModel
import com.alligo.data.remote.ProductApi
import com.alligo.data.utils.NetworkResult
import com.alligo.model.product.Products
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class ProductRepository(
    private val apiService: ProductApi
) {

    val limit = 10

    // Function to search for products
    suspend fun searchProducts(page: Int, query: String): Flow<NetworkResult<out Products>> = flow {
        emit(NetworkResult.Loading)
        val response = apiService.searchProducts(
            search = query,
            limit = limit,
            skip = page * limit
        ) // Assuming pagination logic

        if (response.total != 0) {
            emit(NetworkResult.Success(response.asDomainModel))
        } else {
            emit(NetworkResult.Failure("No products found"))
        }

    }.catch { e ->
        // Handle the error and emit failure
        emit(NetworkResult.Failure(e.message ?: "Unknown Error"))
        Log.e("Error Retrofit", "searchProducts, ${e.message}")
    }

    // Function to get all products
    suspend fun getProducts(page: Int): Flow<NetworkResult<out Products>> = flow {
        emit(NetworkResult.Loading)
        val response =
            apiService.getProducts(limit = limit, skip = page * limit) // Assuming pagination logic

        if (response.total != 0) {
            emit(NetworkResult.Success(response.asDomainModel))
        } else {
            emit(NetworkResult.Failure("No products found"))
        }


    }.catch { e ->

        emit(NetworkResult.Failure(e.message ?: "Unknown Error"))
        Log.e("Error Retrofit", "getProducts, ${e.message}")
    }

    // Function to get a single product by id
    suspend fun getProduct(id: String): Flow<NetworkResult<out Products>> = flow {
        emit(NetworkResult.Loading)
        val response = apiService.getProduct(id = id)
        emit(NetworkResult.Success(response.asDomainModel))

    }.catch { e ->

        emit(NetworkResult.Failure(e.message ?: "Unknown Error"))
        Log.e("Error Retrofit", "getProduct, ${e.message}")
    }
}

