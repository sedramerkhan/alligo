package com.example.e_commercenativexml.data.repository

import com.example.e_commercenativexml.data.local.room.dao.CartDao
import com.example.e_commercenativexml.model.CartItem
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