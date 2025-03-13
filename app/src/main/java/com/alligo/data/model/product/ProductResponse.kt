package  com.alligo.data.model.product

import com.alligo.model.product.Product


data class ProductResponse(
    val id: Int,
    val title: String,
    val discountPercentage: Double,
    val availabilityStatus: String,
    val brand: String?,
    val category: String,
    val description: String,
    val minimumOrderQuantity: Int,
    val price: Double,
    val rating: Double,
    val returnPolicy: String,
    val shippingInformation: String,
    val sku: String,
    val stock: Int,
    val warrantyInformation: String,
    val weight: Int,
    val tags: List<String>,
    val dimensions: DimensionsResponse,
    val thumbnail: String,
    val images: List<String>,
    val meta: MetaResponse,
    val reviews: List<ReviewResponse>,
    )

val ProductResponse.asDomainModel: Product
    get() = Product(
        availabilityStatus = availabilityStatus,
        brand = brand,
        category = category,
        description = description,
        dimensions = dimensions.asDomainModel,  // Convert DimensionsResponse to Domain Model
        discountPercentage = discountPercentage,
        id = id,
        images = images,
        meta = meta.asDomainModel,  // Convert MetaResponse to Domain Model
        minimumOrderQuantity = minimumOrderQuantity,
        price = price,
        rating = rating,
        returnPolicy = returnPolicy,
        reviews = reviews.map { it.asDomainModel },  // Convert ReviewResponse to Domain Model
        shippingInformation = shippingInformation,
        sku = sku,
        stock = stock,
        tags = tags,
        thumbnail = thumbnail,
        title = title,
        warrantyInformation = warrantyInformation,
        weight = weight
    )


