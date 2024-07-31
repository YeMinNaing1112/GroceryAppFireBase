package com.yeminnaing.firebasecomposeproject.domainLayer

sealed class Resources<T>() {
    data class Success<T>(val data: T) : Resources<T>()
    data class Error<T>(val error: String) : Resources<T>()
}