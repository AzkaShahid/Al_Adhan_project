package com.app.models.prayer


import com.google.gson.annotations.SerializedName

data class Method(
    @SerializedName("id")
    var id: Int? = 0,
    @SerializedName("location")
    var location: Location? = Location(),
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("params")
    var params: Params? = Params()
)