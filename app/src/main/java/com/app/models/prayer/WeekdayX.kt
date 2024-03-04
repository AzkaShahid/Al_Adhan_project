package com.app.models.prayer


import com.google.gson.annotations.SerializedName

data class WeekdayX(
    @SerializedName("ar")
    var ar: String? = "",
    @SerializedName("en")
    var en: String? = ""
)