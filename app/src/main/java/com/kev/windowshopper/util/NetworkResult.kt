package com.kev.windowshopper.util

sealed class NetworkResult <T>{
    class Loading<T>() : NetworkResult<T>()
    data class Success<T>(val data: T): NetworkResult<T>()
    data class Error<T>(val message:String): NetworkResult<T>()
}