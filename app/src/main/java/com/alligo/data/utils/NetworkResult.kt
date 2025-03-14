package  com.alligo.data.utils


sealed class NetworkResult<T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Failure(val message: String) : NetworkResult<Nothing>()
    data object Loading : NetworkResult<Nothing>()
    data object None : NetworkResult<Nothing>()
}
