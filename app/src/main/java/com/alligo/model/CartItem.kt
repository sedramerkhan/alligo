package  com.alligo.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class CartItem(
    @PrimaryKey
    val id: Long,
    val title: String,
    val discountPercentage: Double,
    val availabilityStatus: String,
    val brand: String?,
    val category: String,
    val description: String,
    val minimumOrderQuantity: Int,
    val price: Double,
    val rating: Double,
    val thumbnail: String,
    val quantity: Int
)