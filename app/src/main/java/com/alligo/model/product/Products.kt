package  com.alligo.model.product

data class Products(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int
)