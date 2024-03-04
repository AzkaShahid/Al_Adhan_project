package com.app.models.prayer


import com.google.gson.annotations.SerializedName

data class Date(
    @SerializedName("gregorian")
    var gregorian: Gregorian? = Gregorian(),
    @SerializedName("hijri")
    var hijri: Hijri? = Hijri(),
    @SerializedName("readable")
    var readable: String? = "",
    @SerializedName("timestamp")
    var timestamp: String? = ""
)