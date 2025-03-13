package  com.alligo.model.product

import com.alligo.model.CartItem


data class Product(
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
    val dimensions: Dimensions,
    val thumbnail: String,
    val images: List<String>,
    val meta: Meta,
    val reviews: List<Review>,
)

fun Product.toCartItem(quantity: Int): CartItem {
    return CartItem(
        id = this.id.toLong(),
        title = this.title,
        discountPercentage = this.discountPercentage,
        availabilityStatus = this.availabilityStatus,
        brand = this.brand,
        category = this.category,
        description = this.description,
        minimumOrderQuantity = this.minimumOrderQuantity,
        price = this.price,
        rating = this.rating,
        thumbnail = this.thumbnail,
        quantity=quantity
    )
}