package com.example.e_commercenativexml.data.model.product

import com.example.e_commercenativexml.model.product.Product


data class ProductResponse(
    val availabilityStatus: String,
    val brand: String?,
    val category: String,
    val description: String,
    val dimensions: DimensionsResponse,  // Change this to DimensionsResponse
    val discountPercentage: Double,
    val id: Int,
    val images: List<String>,
    val meta: MetaResponse,  // Change this to MetaResponse
    val minimumOrderQuantity: Int,
    val price: Double,
    val rating: Double,
    val returnPolicy: String,
    val reviews: List<ReviewResponse>,  // Change this to ReviewResponse
    val shippingInformation: String,
    val sku: String,
    val stock: Int,
    val tags: List<String>,
    val thumbnail: String,
    val title: String,
    val warrantyInformation: String,
    val weight: Int
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


