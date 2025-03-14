package  com.alligo.data.remote

import com.alligo.data.model.product.ProductResponse
import com.alligo.data.model.product.ProductsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApi {

    @GET("products/search")
    suspend fun searchProducts(
        @Query("search") search: String?,
        @Query("limit") limit: Int,
        @Query("skip") skip: Int,
    ): ProductsResponse

    @GET("products")
    suspend fun getProducts(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int,
    ): ProductsResponse

    @GET("products/{id}")
    suspend fun getProduct(
        @Path("id") id: Int,
    ): ProductResponse
}