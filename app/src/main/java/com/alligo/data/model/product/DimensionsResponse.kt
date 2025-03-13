package  com.alligo.data.model.product

import com.alligo.model.product.Dimensions

data class DimensionsResponse(
    val depth: Double,
    val height: Double,
    val width: Double
)

val DimensionsResponse.asDomainModel: Dimensions
    get() = Dimensions(
        depth = depth,
        height = height,
        width = width
    )
