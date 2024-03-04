package com.app.network


import com.app.constants.AppConstants
import com.app.models.login.LoginUserResponse
import com.app.models.prayer.PrayerResponse
import retrofit2.http.*

/**
 * @author Muzzamil Saleem
 */
interface ApiClientInterface {
    @GET(AppConstants.WebURL.LOGIN)
    suspend fun callLogin(
        @Query("username") userName: String,
        @Query("password") password: String
    ): LoginUserResponse

    @GET()
    suspend fun getPrayerData(@Url string: String): PrayerResponse

}