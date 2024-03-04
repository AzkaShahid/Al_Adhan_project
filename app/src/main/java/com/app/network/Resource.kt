package com.app.network

import okhttp3.ResponseBody

/**
 * @author Muzzamil Saleem
 */
sealed class Resource<out T> {
    data class Success<out T>(val value: T) : Resource<T>()

    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?,
        val errorString: String?,

    ) : Resource<Nothing>()

    object Loading : Resource<Nothing>()

}