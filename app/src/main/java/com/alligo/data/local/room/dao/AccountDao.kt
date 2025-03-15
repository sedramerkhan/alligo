package com.alligo.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.alligo.model.Account
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {

    @Query("SELECT * FROM Account WHERE id LIKE :id")
    fun get(id: Long): Flow<Account?>


    @Upsert
    suspend fun insert(item: Account): Long


    @Query("DELETE FROM Account")
    suspend fun delete()


}