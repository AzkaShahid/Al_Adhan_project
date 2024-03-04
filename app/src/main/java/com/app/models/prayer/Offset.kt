package com.app.models.prayer


import com.google.gson.annotations.SerializedName

data class Offset(
    @SerializedName("Asr")
    var asr: Int? = 0,
    @SerializedName("Dhuhr")
    var dhuhr: Int? = 0,
    @SerializedName("Fajr")
    var fajr: Int? = 0,
    @SerializedName("Imsak")
    var imsak: Int? = 0,
    @SerializedName("Isha")
    var isha: Int? = 0,
    @SerializedName("Maghrib")
    var maghrib: Int? = 0,
    @SerializedName("Midnight")
    var midnight: Int? = 0,
    @SerializedName("Sunrise")
    var sunrise: Int? = 0,
    @SerializedName("Sunset")
    var sunset: Int? = 0
)