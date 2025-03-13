package  com.alligo.data.repository

import com.alligo.data.local.room.dao.CartDao
import com.alligo.model.CartItem
import kotlinx.coroutines.flow.Flow

class CartRepository(
    private val cartDao: CartDao
) {

    fun getAllItems(): Flow<List<CartItem>> = cartDao.getAll()

    fun getItemById(id: Long): Flow<CartItem?> = cartDao.getItem(id)

    suspend fun insertItem(item: CartItem): Long {
        return cartDao.insert(item)
    }

    suspend fun deleteItem(id: Long) {
        cartDao.delete(id)
    }

    suspend fun deleteAll() {
        cartDao.deleteAll()
    }
}