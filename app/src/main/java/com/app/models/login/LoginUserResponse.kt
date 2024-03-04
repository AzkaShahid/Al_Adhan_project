package com.app.models.login


import com.google.gson.annotations.SerializedName

data class LoginUserResponse(
    @SerializedName("message")
    var message: String? = "",
    @SerializedName("status")
    var statusCode: Int? = 0,
    @SerializedName("cuid")
    var cuid: String? = "",
    @SerializedName("error_code")
    var errorCode: String? = ""
)