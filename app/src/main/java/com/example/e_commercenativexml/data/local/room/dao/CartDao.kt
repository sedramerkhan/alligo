package com.example.e_commercenativexml.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.e_commercenativexml.model.CartItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Query("SELECT * FROM CartItem")
    fun getAll(): Flow<List<CartItem>>

    @Query("SELECT * FROM CartItem WHERE id LIKE :id")
    fun getItem(id: Long): Flow<CartItem?>


    @Upsert
    suspend fun insert(item: CartItem):Long

    @Update
    suspend fun update(item: CartItem)

    @Query("DELETE FROM CartItem WHERE id LIKE :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM CartItem")
    suspend fun deleteAll()
}