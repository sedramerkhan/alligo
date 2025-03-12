package com.example.e_commercenativexml.data.local.room


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.e_commercenativexml.data.local.room.dao.CartDao
import com.example.e_commercenativexml.model.CartItem


@Database(
    entities = [CartItem::class],
    version = 1,
    exportSchema = false
)
abstract class Room : RoomDatabase() {
    abstract fun cartItemDao(): CartDao


}