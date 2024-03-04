package com.app.models.prayer


import com.google.gson.annotations.SerializedName

data class Timings(
    @SerializedName("Asr")
    var asr: String? = "",
    @SerializedName("Dhuhr")
    var dhuhr: String? = "",
    @SerializedName("Fajr")
    var fajr: String? = "",
    @SerializedName("Firstthird")
    var firstthird: String? = "",
    @SerializedName("Imsak")
    var imsak: String? = "",
    @SerializedName("Isha")
    var isha: String? = "",
    @SerializedName("Lastthird")
    var lastthird: String? = "",
    @SerializedName("Maghrib")
    var maghrib: String? = "",
    @SerializedName("Midnight")
    var midnight: String? = "",
    @SerializedName("Sunrise")
    var sunrise: String? = "",
    @SerializedName("Sunset")
    var sunset: String? = ""
)