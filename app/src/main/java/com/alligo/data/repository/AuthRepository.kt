package com.alligo.data.repository

import android.util.Log
import com.alligo.data.local.room.dao.AccountDao
import com.alligo.data.model.auth.AccountResponse
import com.alligo.data.model.auth.asDomainModel
import com.alligo.data.remote.AuthApi
import com.alligo.data.utils.NetworkResult
import com.alligo.model.Account
import com.alligo.presentation.BaseApplication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class AuthRepository(
    private val apiService: AuthApi,
    private val accountDao: AccountDao
) {

    suspend fun login(
        userName: String,
        password: String
    ): Flow<NetworkResult<out Account>> = flow {
        emit(NetworkResult.Loading)
        val response = apiService.login(
            userName = userName,
            password = password
        ) // Assuming pagination logic
        Log.e("Error Retrofit", "login, ${response}")


        accountDao.insert(response.asDomainModel)
        BaseApplication.appPreferences.token = response.accessToken

        emit(NetworkResult.Success(response.asDomainModel))


    }.catch { e ->
        // Handle the error and emit failure
        emit(NetworkResult.Failure(e.message ?: "Unknown Error"))
        Log.e("Error Retrofit", "login, ${e.message}")
    }
}
