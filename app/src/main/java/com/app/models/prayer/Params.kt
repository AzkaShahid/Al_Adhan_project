package com.app.models.prayer


import com.google.gson.annotations.SerializedName

data class Params(
    @SerializedName("Fajr")
    var fajr: Int? = 0,
    @SerializedName("Isha")
    var isha: Int? = 0
)