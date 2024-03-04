package com.app.models.prayer


import com.google.gson.annotations.SerializedName

data class PrayerResponse(
    @SerializedName("code")
    var code: Int? = 0,
    @SerializedName("data")
    var `data`: List<Data>? = listOf(),
    @SerializedName("status")
    var status: String? = ""
)