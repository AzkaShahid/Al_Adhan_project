package com.app.models.prayer


import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("latitude")
    var latitude: Double? = 0.0,
    @SerializedName("longitude")
    var longitude: Double? = 0.0
)