package com.example.e_commercenativexml.data.model.product

import com.example.e_commercenativexml.model.product.Meta

data class MetaResponse(
    val barcode: String,
    val createdAt: String,
    val qrCode: String,
    val updatedAt: String
)


val MetaResponse.asDomainModel : Meta
    get() = Meta(
        barcode=barcode,
        createdAt=createdAt,
        qrCode = qrCode,
        updatedAt=updatedAt
    )