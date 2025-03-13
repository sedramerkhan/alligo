package  com.alligo.data.model.product

import com.alligo.model.product.Products


data class ProductsResponse(
    val limit: Int,
    val products: List<ProductResponse>,  // Change this to ProductResponse
    val skip: Int,
    val total: Int
)

val ProductsResponse.asDomainModel: Products
    get() = Products(
        limit = limit,
        products = products.map { it.asDomainModel },  // Convert ProductResponse to Domain Model
        skip = skip,
        total = total
    )
