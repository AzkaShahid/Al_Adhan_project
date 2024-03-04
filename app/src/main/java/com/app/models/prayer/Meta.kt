package com.app.models.prayer


import com.google.gson.annotations.SerializedName

data class Meta(
    @SerializedName("latitude")
    var latitude: Double? = 0.0,
    @SerializedName("latitudeAdjustmentMethod")
    var latitudeAdjustmentMethod: String? = "",
    @SerializedName("longitude")
    var longitude: Double? = 0.0,
    @SerializedName("method")
    var method: Method? = Method(),
    @SerializedName("midnightMode")
    var midnightMode: String? = "",
    @SerializedName("offset")
    var offset: Offset? = Offset(),
    @SerializedName("school")
    var school: String? = "",
    @SerializedName("timezone")
    var timezone: String? = ""
)