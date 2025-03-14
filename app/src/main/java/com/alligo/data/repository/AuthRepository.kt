package com.alligo.data.repository

import android.util.Log
import com.alligo.data.model.auth.Account
import com.alligo.data.remote.AuthApi
import com.alligo.data.utils.NetworkResult
import com.alligo.presentation.BaseApplication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class AuthRepository(
    private val apiService: AuthApi
) {

    suspend fun login(userName: String, password: String): Flow<NetworkResult<out Account>> = flow {
        emit(NetworkResult.Loading)
        val response = apiService.login(
            userName = userName,
            password = password
        ) // Assuming pagination logic
        Log.e("Error Retrofit", "login, ${response}")


        BaseApplication.appPreferences.token = response.accessToken
        emit(NetworkResult.Success(response))


    }.catch { e ->
        // Handle the error and emit failure
        emit(NetworkResult.Failure(e.message ?: "Unknown Error"))
        Log.e("Error Retrofit", "login, ${e.message}")
    }
}
