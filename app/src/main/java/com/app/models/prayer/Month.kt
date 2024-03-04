package com.app.models.prayer


import com.google.gson.annotations.SerializedName

data class Month(
    @SerializedName("en")
    var en: String? = "",
    @SerializedName("number")
    var number: Int? = 0
)