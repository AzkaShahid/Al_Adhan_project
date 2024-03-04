package com.app.bases

import com.app.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

/**
 * @author Muzzamil Saleem
 */
abstract class BaseRepository {
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        Resource.Failure(
                            false,
                            throwable.code(),
                            throwable.response()?.errorBody(),
                            throwable.message()
                        )
                    }
                    else -> {
                        Resource.Failure(true, null, null, throwable.message)
                    }
                }
            }
        }
    }
}