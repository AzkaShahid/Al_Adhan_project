package com.app.models.prayer


import com.google.gson.annotations.SerializedName

data class Weekday(
    @SerializedName("en")
    var en: String? = ""
)