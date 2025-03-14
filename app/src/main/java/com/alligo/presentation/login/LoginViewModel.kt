package com.alligo.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alligo.data.model.auth.Account
import com.alligo.data.repository.AuthRepository
import com.alligo.data.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
     private  val authRepository: AuthRepository
) : ViewModel() {


    private val _loginState = MutableStateFlow<NetworkResult<out Account>>(NetworkResult.None)
    val loginState: StateFlow<NetworkResult<out Account>> = _loginState

    fun login(userName: String, password: String) {

        Log.i("login","view model $userName $password")

        viewModelScope.launch {
            authRepository.login(userName, password)
                .onStart { _loginState.value = NetworkResult.Loading } // Set loading state
                .catch { e -> _loginState.value = NetworkResult.Failure(e.message ?: "Unknown Error") }
                .collect { result -> _loginState.value = result }
        }
    }
}