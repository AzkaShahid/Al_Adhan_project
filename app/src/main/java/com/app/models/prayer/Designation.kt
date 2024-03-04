package com.app.models.prayer


import com.google.gson.annotations.SerializedName

data class Designation(
    @SerializedName("abbreviated")
    var abbreviated: String? = "",
    @SerializedName("expanded")
    var expanded: String? = ""
)